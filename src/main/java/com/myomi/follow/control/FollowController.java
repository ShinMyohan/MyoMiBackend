package com.myomi.follow.control;

import com.myomi.follow.dto.FollowDeleteRequestDto;
import com.myomi.follow.dto.FollowReadResponseDto;
import com.myomi.follow.service.FollowService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@RestController
@RequiredArgsConstructor
public class FollowController {
	private final FollowService followService;
	
	//팔로우 하기(스토어)
	@PostMapping("store/follow/{sId}")
	public void followSave(@PathVariable String sId, Authentication user){
		followService.addFollow(sId,user);
	}
	
	//언팔로우 하기(스토어)
	@DeleteMapping("store/follow/{sId}")
	public void followStoreDelete(@PathVariable String sId, Authentication user) {
		followService.removeStoreFollow(sId,user);
	}
	
	//팔로우 목록 조회(마이페이지,페이징)
	@GetMapping("mypage/follow")
	public List<FollowReadResponseDto> followAllByUserList(Authentication user,@PageableDefault(size=5) Pageable pageable) {
		return followService.getAllUserFollowList(user,pageable);
	}
	
	//팔로우 여부 확인
	@GetMapping("store/follow/{sId}")
	public int followCheck(@PathVariable String sId, Authentication user) {
		return followService.getFollowCheck(sId,user);
	}
	
}
