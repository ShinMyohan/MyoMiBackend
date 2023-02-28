package com.myomi.follow.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.follow.dto.FollowDeleteRequestDto;
import com.myomi.follow.dto.FollowReadResponseDto;
import com.myomi.follow.service.FollowService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RestController
@RequiredArgsConstructor
public class FollowController {
	private final FollowService followService;
	
	//팔로우 하기
	@PostMapping("store/follow/{sId}")
	public void followSave(@PathVariable String sId, Authentication user){
		followService.addFollow(sId,user);
	}
	
	//언팔로우 하기(마이페이지,다중삭제 가능)
	@DeleteMapping("mypage/follow")
	public void followMypageDelete(@RequestBody List<FollowDeleteRequestDto> requestDto, Authentication user ) {
		followService.removeMypageFollow(requestDto,user);
	}
	
	//팔로우 목록 조회(마이페이지,페이징)
	@GetMapping("mypage/follow")
	public List<FollowReadResponseDto> followAllByUserList(Authentication user,@PageableDefault(size=5) Pageable pageable) {
		return followService.getAllUserFollowList(user,pageable);
	}
	
	//언팔로우 하기(스토어)
	@DeleteMapping("store/{sId}")
	public void followStoreDelete(@PathVariable String sId, Authentication user) {
		followService.removeStoreFollow(sId,user);
	}

}
