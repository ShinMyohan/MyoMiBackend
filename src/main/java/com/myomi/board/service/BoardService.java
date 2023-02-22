package com.myomi.board.service;


//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myomi.board.dto.BoardAddRequestDto;
import com.myomi.board.dto.BoardEditRequestDto;
import com.myomi.board.dto.BoardDetailResponseDto;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
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
	//   @Autowired 
	//   private BoardRepository br;
	private final BoardRepository br;
	private final UserRepository ur;

	@Transactional
	public List<BoardReadResponseDto> findBoard(Pageable pageable) {
		//  Sort sort = sort.by(Direction.DESC,"createdDate");
		List<Board> list = br.findAll(pageable);
		List <BoardReadResponseDto> boardList = new ArrayList<>();
		for (Board board : list) {
			BoardReadResponseDto dto = BoardReadResponseDto.builder()
					.bNum(board.getBNum())
				  //  .user(board.getUser())
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

	
	@Transactional
	public BoardDetailResponseDto detailBoard(Long bNum) {
		Board board = br.findById(bNum).get();
		return new BoardDetailResponseDto(board);
	}
	

	@Transactional
	public void addBoard (BoardAddRequestDto addDto) {
		//String userId = addDto.getUser().getId();
		LocalDateTime date = LocalDateTime.now();
		Optional<User> optU = ur.findById("id1");
        Board board = new Board();
		board = Board.builder()
				.user(optU.get())
				.category(addDto.getCategory())
				.title(addDto.getTitle())
				.content(addDto.getContent())
				.createdDate(date)
				.hits(addDto.getHits())
				.build();

		br.save(board);

	}

	@Transactional
	public BoardDetailResponseDto modifyBoard(BoardEditRequestDto editDto, Long bNum) {
		Board board = br.findById(bNum).get();
		board.update(editDto.getCategory(), editDto.getContent(), editDto.getTitle());
		return new BoardDetailResponseDto(board);

	}
	
	@Transactional
	public void deleteBoard(Long bNum) {
		Board board = br.findById(bNum).get();
		br.delete(board);
	}

}
