package com.myomi.board.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.board.dto.BoardDetailResponseDto;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.dto.PageBean;
import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;
import com.myomi.exception.AddException;
import com.myomi.exception.RemoveException;
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
		int totalCnt = (int) br.count();
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
	public ResponseEntity<BoardReadResponseDto> addBoard(BoardReadResponseDto addDto, Authentication user) throws IOException {

		String username = user.getName();
		Optional<User> optU = ur.findById(username);
		MultipartFile file = addDto.getFile();
		//log.info("서비스 업로더 전:" + file.getName() + "사이즈는: "+ file.getSize());
		if (file != null) {
			InputStream inputStream = file.getInputStream();
			boolean isValid = FileUtils.validImgFile(inputStream);
			if (!isValid) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			String fileUrl = s3Uploader.upload(file, "게시판이미지", user, addDto);
			//log.info("서비스:" + file.getName() + "사이즈는: "+ file.getSize());
			Board board = addDto.toEntity(optU.get(), fileUrl);
			br.save(board);

		} else {
			Board board = addDto.toEntity(optU.get(), null);
			br.save(board);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//글 수정
	@Transactional
	public ResponseEntity<BoardDetailResponseDto> modifyBoard(BoardReadResponseDto editDto, Long boardNum, Authentication user)
			throws AddException, IOException {

		String username = user.getName();
		Board board = br.findById(boardNum).get();
		MultipartFile file = editDto.getFile();

		if (!board.getUser().getId().equals(username)) {
			throw new AddException("작성자만 수정 가능합니다.");
		} else {
			board.update(editDto.getCategory(), editDto.getTitle(), editDto.getContent());

			if (file!= null) {
				String fileUrl = s3Uploader.upload(file, "게시판이미지", user, editDto);
				board.update(fileUrl);
			}

		}
		return new ResponseEntity<>(HttpStatus.OK);
	}



	//글 삭제
	@Transactional
	public void deleteBoard(Long boardNum, Authentication user) throws RemoveException {
		String username = user.getName();
		Board board = br.findById(boardNum).get();
		if (board.getUser().getId().equals(username)) {
			br.delete(board);
		} else {
			throw new RemoveException("작성자만 삭제 가능합니다.");
		}
	}

	//마이페이지에서 내가 작성한 글 보기
	@Transactional
	public List<BoardReadResponseDto> findBoardListByUser(Authentication user,
			Pageable pageable) {
		String username = user.getName();
		List<Board> list = br.findAllByUser(username, pageable);
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

