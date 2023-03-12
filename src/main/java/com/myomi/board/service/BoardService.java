package com.myomi.board.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.dto.PageBean;
import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;
import com.myomi.common.status.AddException;
import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.ExceedMaxUploadSizeException;
import com.myomi.common.status.ResponseDetails;
import com.myomi.common.status.TokenValidFailedException;
import com.myomi.s3.FileUtils;
import com.myomi.s3.S3UploaderBoard;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class BoardService {
	@Autowired
	private S3UploaderBoard s3Uploader;
	private final BoardRepository br;
	private final UserRepository ur;
	private final CommentRepository cr;


	//글 리스트 출력
	public PageBean<BoardReadResponseDto> getBoard(int currentPage) {
		int startRow = (currentPage-1)*PageBean.CNT_PER_PAGE +1;
		int endRow =  currentPage*PageBean.CNT_PER_PAGE;
		List<Object[]> list = br.findAll(startRow, endRow);
		List<BoardReadResponseDto> boardList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //HH:mm:ss.SSSSSS");
		for (Object[] arr : list) {
			String timeStr= arr[4].toString().split(" ")[0] + " 00:00:00";
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(((BigDecimal)arr[1]).longValue())
					.category((String)arr[2])
					.title((String)arr[3])
					.createdDate(LocalDateTime.parse(timeStr,format))
					.userName((String)arr[5])
					.hits(((BigDecimal)arr[6]).longValue())
					.build();
			boardList.add(dto);
		}
		int totalCnt = (int) br.count();
		PageBean<BoardReadResponseDto> pb = new PageBean(currentPage, boardList, totalCnt);
		return pb;
	}	

	//제목으로 검색
	public List<BoardReadResponseDto> getByTitle(String keyword, Pageable pageable) {
		List<Board> list = br.findByTitleContaining(keyword, pageable);
		List<BoardReadResponseDto> boardList = new ArrayList<>();
		for (Board board : list) {
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(board.getBoardNum())
					.userName(board.getUser().getName())
					.category(board.getCategory())
					.title(board.getTitle())
					.content(board.getContent())
					.createdDate(board.getCreatedDate())
					.hits(board.getHits())
					.build();
			boardList.add(dto);
		}
		return boardList;
	}

	//제목, 카테고리로 검색
	public PageBean<BoardReadResponseDto> getByCategoryAndTitle(int currentPage, String category, String title) {
		int startRow = (currentPage-1)*PageBean.CNT_PER_PAGE +1;
		int endRow =  currentPage*PageBean.CNT_PER_PAGE;
		List<Object[]> list = br.findByCategoryAndTitle(startRow, endRow, category, title);
		List<BoardReadResponseDto> boardList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //HH:mm:ss.SSSSSS");
		for (Object[] arr : list) {
			String timeStr= arr[4].toString().split(" ")[0] + " 00:00:00";
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(((BigDecimal)arr[1]).longValue())
					.category((String)arr[2])
					.title((String)arr[3])
					.createdDate(LocalDateTime.parse(timeStr,format))
					.userName((String)arr[5])
					.hits(((BigDecimal)arr[6]).longValue())
					.build();
			boardList.add(dto);
		}
		List<Board> bList = br.findByCategoryContainingAndTitleContaining(category, title);
		int totalCnt = bList.size();
		PageBean<BoardReadResponseDto> pb = new PageBean(currentPage, boardList, totalCnt);
		return pb;
	}	

	//글 상세보기
	@Transactional
	public BoardReadResponseDto detailBoard(Long boardNum, Authentication user) {
		boolean enableUpdate = false;
		boolean enableDelete = false;
		Optional<Board> board = br.findById(boardNum);
		br.updateHits(boardNum);//조회수 늘리기
		List<Comment> list = board.get().getComments();
		List<CommentDto> listCommentDTO = new ArrayList<>();
		if (user == null) {
			for (Comment c : list) {
				CommentDto cDto = new CommentDto();
				listCommentDTO.add(cDto.toDto(c, enableUpdate, enableDelete));
			}
			BoardReadResponseDto dto = new BoardReadResponseDto();
			return dto.toDto(board.get(), listCommentDTO, enableUpdate, enableDelete);

		}else {
			String username = user.getName();
			Optional<User> optU = ur.findById(username);

			if (optU.get().getName() == board.get().getUser().getName()) {
				enableUpdate = true;
				enableDelete = true;
			}
			for (Comment c : list) {
				if (optU.get().getName() == c.getUser().getName()) {
					enableUpdate = true;
					enableDelete = true;
				}
				CommentDto cDto = new CommentDto();
				listCommentDTO.add(cDto.toDto(c, enableUpdate, enableDelete));
			}
			BoardReadResponseDto dto = new BoardReadResponseDto();
			return dto.toDto(board.get(), listCommentDTO, enableUpdate, enableDelete);
		}
	}

	//글 작성
	@Transactional
	public ResponseDetails addBoard(BoardReadResponseDto addDto, Authentication user) throws IOException {
		String path = "/api/board";
		User u = ur.findById(user.getName())
				.orElseThrow(() -> new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "로그인한 회원만 글 작성이 가능합니다."));
		String username = user.getName();
		Optional<User> optU = ur.findById(username);
		MultipartFile file = addDto.getFile();
		//log.info("서비스 업로더 전:" + file.getName() + "사이즈는: "+ file.getSize());
		if (file != null) {
			InputStream inputStream = file.getInputStream();
			boolean isValid = FileUtils.validImgFile(inputStream);
			if (!isValid) {
				throw new ExceedMaxUploadSizeException(ErrorCode.BAD_REQUEST,"EXCEED_FILE_SIZE");
			}
			Long maxSize = (long) (5 * 1024 * 1024);
			if (file.getSize() > maxSize) {
				log.info("첨부가능한 파일의 용량을 초과하였습니다.");
				//return new ResponseDetails("파일은 5MB까지 첨부 가능합니다", 400, path);
				throw new ExceedMaxUploadSizeException (ErrorCode.BAD_REQUEST,"EXCEED_FILE_SIZE");
			}else {
				String fileUrl = s3Uploader.upload(file, "게시판이미지", user, addDto);
				//log.info("서비스:" + file.getName() + "사이즈는: "+ file.getSize());
				Board board = addDto.toEntity(optU.get(), fileUrl);
				br.save(board);
			}
		} else {
			Board board = addDto.toEntity(optU.get(), null);
			br.save(board);
		}
		return new ResponseDetails (addDto, 200, path);
	}

	//글 수정
	@Transactional
	public ResponseDetails modifyBoard(BoardReadResponseDto editDto, Long boardNum, Authentication user)
			throws AddException, IOException {
		String path = "/api/board";
		String username = user.getName();
		Board board = br.findById(boardNum).get();
		MultipartFile file = editDto.getFile();

		if (!board.getUser().getId().equals(username)) {
			throw new AddException(ErrorCode.BAD_REQUEST, "작성자만 수정 가능합니다.");
		} else {
			board.update(editDto.getCategory(), editDto.getTitle(), editDto.getContent());

			if (file!= null) {
				Long maxSize = (long) (5 * 1024 * 1024);
				if (file.getSize() > maxSize) {
					log.info("첨부가능한 파일의 용량을 초과하였습니다.");
					throw new ExceedMaxUploadSizeException (ErrorCode.BAD_REQUEST,"EXCEED_FILE_SIZE");

				}else {
					String fileUrl = s3Uploader.upload(file, "게시판이미지", user, editDto);
					board.update(fileUrl);
				}
			}
		}
		return new ResponseDetails (editDto, 200, path);
	}

	//글 삭제
	@Transactional
	public void deleteBoard(Long boardNum, Authentication user) {
		String username = user.getName();
		Board board = br.findById(boardNum).get();
		if (board.getUser().getId().equals(username)) {
			br.delete(board);
		} 
	}

	//마이페이지에서 내가 작성한 글 보기
	@Transactional
	public List<BoardReadResponseDto> findBoardListByUser(Authentication user) {
		String path = "/api/myboardlist";
		User u = ur.findById(user.getName())
				.orElseThrow(() -> new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "로그인한 회원만 이용가능한 서비스입니다."));
		String username = user.getName();
		List<Board> list = br.findAllByUser(username);
		List<BoardReadResponseDto> boardList = new ArrayList<>();
		for (Board board : list) {
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(board.getBoardNum())
					.category(board.getCategory())
					.title(board.getTitle())
					.createdDate(board.getCreatedDate())
					.hits(board.getHits())
					.build();
			boardList.add(dto);
		}
		return boardList;
	}
}

