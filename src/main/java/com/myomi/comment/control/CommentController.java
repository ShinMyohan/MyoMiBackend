package com.myomi.comment.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j

public class CommentController {
     private final CommentService service;
       
     //마이페이지에서 내가 작성한 댓글 보기
     @GetMapping("mypage/commentList")
     public List<CommentDto> myCommentList(Authentication user,
    		 @PageableDefault(size=4) Pageable pageable){
    	 return service.findMyCommentList(user,pageable);
     }
    

}
