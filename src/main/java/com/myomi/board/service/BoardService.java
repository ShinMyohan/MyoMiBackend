package com.myomi.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.board.dto.BoardDetailResponseDto;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;
import com.myomi.exception.AddException;
import com.myomi.exception.RemoveException;
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

	private final BoardRepository br;
	private final UserRepository ur;
	private final CommentRepository cr;


	//글 리스트 출력 
	public List<BoardReadResponseDto> findBoard(Pageable pageable) {
		//  Sort sort = sort.by(Direction.DESC,"createdDate");
		List <Board> list = br.findAll(pageable);
		List <BoardReadResponseDto> boardList = new ArrayList<>();
		for (Board board : list) {
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(board.getBoardNum())
					.user(board.getUser())
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

	//제목으로 검색 
	public List<BoardReadResponseDto> findByTitle(String keyword, Pageable pageable) {
		List<Board> list = br.findByTitleContaining(keyword, pageable);
		List <BoardReadResponseDto> boardList = new ArrayList<>();
		for (Board board : list) {
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(board.getBoardNum())
					.user(board.getUser())
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
	public List<BoardReadResponseDto> findByCategoryAndTitle(String category, String title, Pageable pageable) {
		List<Board> list = br.findByCategoryContainingAndTitleContaining(category, title, pageable);
		List <BoardReadResponseDto> boardList = new ArrayList<>();
		for (Board board : list) {
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.boardNum(board.getBoardNum())
					.user(board.getUser())
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

	//글 상세보기 
	@Transactional
	public BoardReadResponseDto detailBoard(Long boardNum) {
		Board board = br.findBoardById(boardNum);
		BoardReadResponseDto dto = BoardReadResponseDto.builder()
				.boardNum(board.getBoardNum())
				.user(board.getUser())
				.category(board.getCategory())
				.title(board.getTitle())
				.content(board.getContent())
				.createdDate(board.getCreatedDate())
				.hits(board.getHits())
				.comments(board.getComments())
				.build();

		return dto;
	}

	//글 작성 
	@Transactional
	public ResponseEntity<BoardReadResponseDto> addBoard (BoardReadResponseDto addDto, Authentication user) {

		String username = user.getName();
		Optional<User> optU = ur.findById(username);
		Board board = addDto.toEntity(optU.get());
		br.save(board);
		return new ResponseEntity<>(HttpStatus.OK);
	}



	//글 수정 
	@Transactional
	public BoardDetailResponseDto modifyBoard(BoardReadResponseDto editDto, Long bNum, Authentication user)
	   										throws AddException{
		String username = user.getName();
		Board board = br.findById(bNum).get();
		if (board.getUser().getId().equals(username)) {
			board.update(editDto.getCategory(), editDto.getContent(), editDto.getTitle());
		}else {
			throw new AddException("작성자만 수정 가능합니다.");
		}
		return new BoardDetailResponseDto(board);
	}


	//글 삭제 
	@Transactional
	public void deleteBoard(Long boardNum, Authentication user) throws RemoveException{
		String username = user.getName();
		Board board = br.findById(boardNum).get();
		if (board.getUser().getId().equals(username)) {
			br.delete(board);
		}else {
			throw new RemoveException("작성자만 삭제 가능합니다.");
		}
	}

	//마이페이지에서 내가 작성한 글 보기 
		@Transactional
		public List<BoardReadResponseDto> findBoardListByUser (Authentication user,
				Pageable pageable) {
			String username = user.getName();
			List<Board> list = br.findAllByUser(username, pageable);
			List <BoardReadResponseDto> boardList = new ArrayList<>();
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


	//-------------------댓글--------------------

	//댓글 작성 
	@Transactional
	public ResponseEntity<CommentDto> addComment(CommentDto cDto, Authentication user, Long boardNum){
		LocalDateTime date = LocalDateTime.now();
		String username = user.getName();
		Optional<Board> optB = br.findById(boardNum);
		Optional<User> optU = ur.findById(username);
		Comment comment = cDto.toEntity(optU.get(), optB.get());
		cr.save(comment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//댓글 수정
	@Transactional
	public CommentDto modifyComment (CommentDto cDto, Authentication user, Long boardNum,
			Long commentNum) throws AddException{
		String username = user.getName();
		Optional<Board> optB = br.findById(boardNum);
		Optional<Comment> optC = cr.findById(commentNum);
		Comment comment = optC.get();
		if (comment.getUser().getId().equals(username)) {
			comment.update(cDto.getContent());
		}else {
			throw new AddException("작성자만 수정 가능합니다.");
		}
		return cDto;
	}

	//댓글 삭제
	@Transactional
	public void deleteComment (Authentication user, Long boardNum,
			Long commentNum) throws RemoveException{
		String username = user.getName();
		Optional<Board> optB = br.findById(boardNum);
		Optional<Comment> optC = cr.findById(commentNum);
		Comment comment = optC.get();
		if (comment.getUser().getId().equals(username)) {
			cr.delete(comment);
		}else {
			throw new RemoveException("작성자만 삭제 가능합니다.");
		}
	}
}



