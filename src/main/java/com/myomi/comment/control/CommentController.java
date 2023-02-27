package com.myomi.comment.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.service.CommentService;
import com.myomi.exception.FindException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "댓글")
public class CommentController {
     private final CommentService service;
       
     @ApiOperation(value = "마이페이지 | 내가 작성한 댓글 보기 ")
     @GetMapping("mypage/commentList")
     public ResponseEntity<?> myCommentList(Authentication user,
    		 @PageableDefault(size=4) Pageable pageable){
    	 List<CommentDto> list = service.findMyCommentList(user,pageable);
    	 return new ResponseEntity<>(list, HttpStatus.OK);
     }
    

}
