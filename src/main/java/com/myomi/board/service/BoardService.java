package com.myomi.board.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.board.dto.BoardDetailResponseDto;
import com.myomi.board.dto.BoardReadResponseDto;
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
    public List<BoardReadResponseDto> getBoard(Pageable pageable) {
        //  Sort sort = sort.by(Direction.DESC,"createdDate");
        Page<Board> list = br.findAll(pageable);
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
    public List<BoardReadResponseDto> getByCategoryAndTitle(String category, String title, Pageable pageable) {
        List<Board> list = br.findByCategoryContainingAndTitleContaining(category, title, pageable);
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

    //글 상세보기
    @Transactional
    public BoardReadResponseDto detailBoard(Long boardNum, Authentication user) {
        String username = user.getName();
        Optional<User> optU = ur.findById(username);
        Optional<Board> board = br.findById(boardNum);
        br.updateHits(boardNum);//조회수 늘리기
        List<Comment> list = board.get().getComments();
        List<CommentDto> listCommentDTO = new ArrayList<>();

        boolean enableUpdate = false;
        boolean enableDelete = false;
        System.out.println("로그인한 유저ㅓㅓㅓ>>>>>>:" + optU.get().getName());
        System.out.println("댓글쓴이ㅣㅣㅣㅓㅓㅓ>>>>>>:" + board.get().getUser().getName());

        if (optU.get().getName() == board.get().getUser().getName()) {
            enableUpdate = true;
            enableDelete = true;
        }

        for (Comment c : list) {
            if (optU.get().getName() == c.getUser().getName()) {
                enableUpdate = true;
                enableDelete = true;
            }
            CommentDto cDto = CommentDto.builder()
                    .boardNum(c.getBoard().getBoardNum())
                    .userName(c.getUser().getName())
                    .user(c.getUser())
                    .content(c.getContent())
                    .createdDate(c.getCreatedDate())
                    .commentNum(c.getCommentNum())
                    .parent(c.getParent())
                    .enableDelete(enableDelete)
                    .enableUpdate(enableUpdate)
                    .build();
            listCommentDTO.add(cDto);
        }

        BoardReadResponseDto dto = BoardReadResponseDto.builder()
                .boardNum(board.get().getBoardNum())
                .userName(board.get().getUser().getName())
                .category(board.get().getCategory())
                .title(board.get().getTitle())
                .content(board.get().getContent())
                .createdDate(board.get().getCreatedDate())
                .hits(board.get().getHits())
                .boardImgUrl(board.get().getBoardImgUrl())
                .comments(listCommentDTO)
                .enableDelete(enableDelete)
                .enableUpdate(enableUpdate)
                .build();
        return dto;
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
        if (file != null) {
            InputStream inputStream = file.getInputStream();
            boolean isValid = FileUtils.validImgFile(inputStream);
            if (!isValid) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (!board.getUser().getId().equals(username)) {
                throw new AddException("작성자만 수정 가능합니다.");
            }
            String fileUrl = s3Uploader.upload(file, "게시판이미지", user, editDto);
            board.update(editDto.getCategory(), editDto.getTitle(), editDto.getContent(), fileUrl);

        } else if (file == null) {
            if (!board.getUser().getId().equals(username)) {
                throw new AddException("작성자만 수정 가능합니다.");
            } else {
                board.update(editDto.getCategory(), editDto.getTitle(), editDto.getContent(), null);
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

