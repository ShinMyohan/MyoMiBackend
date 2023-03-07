package com.myomi.follow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.myomi.follow.dto.FollowDeleteRequestDto;
import com.myomi.follow.dto.FollowReadResponseDto;
import com.myomi.follow.entity.Follow;
import com.myomi.follow.repository.FollowRepository;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
	private final FollowRepository fr;
	private final UserRepository ur;
	private final SellerRepository sr;
	
	/* TODO : 1.팔로우 하기@
	 *        2.언팔로우 하기(마이페이지, 다중삭제)@
	 *        3.팔로우 목록 조회(페이징)@
	 *        4.언팔로우 하기(스토어)@
	 */
	
	//팔로우 하기
	@Transactional
	public void addFollow(String sellerId, Authentication user) {
		String userId = user.getName();
		Optional<Follow> optF = fr.findByUserIdAndSellerId(userId, sellerId);
		if(optF.isPresent()) {
			log.info("이미 팔로우 된 셀러입니다.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			Optional<User> optU = ur.findById(userId);
			Optional<Seller> optS = sr.findById(sellerId);
			Follow follow = new Follow(optU.get(),optS.get());
			fr.save(follow);
		}
	}
	
	//언팔로우 하기 (마이페이지, 다중삭제)
	@Transactional
	public void removeMypageFollow(List<FollowDeleteRequestDto> requestDto ,Authentication user) {
		String userId = user.getName();
		for (FollowDeleteRequestDto follow : requestDto) {
			fr.deleteFollowByUserIdAndSellerId(userId, follow.getSellerId());
		}	
	}
	
	//3.팔로우 목록 조회(페이징)
	@Transactional
	public List<FollowReadResponseDto> getAllUserFollowList(Authentication user,Pageable pageable){
		String userId = user.getName();
		Optional<User> optU = ur.findById(userId);
		List<Follow> follows = fr.findAllByUserId(optU.get().getId(),pageable);
		List<FollowReadResponseDto> list = new ArrayList<>();
		if(follows.size() == 0) {
			log.info("팔로우가 없습니다.");
		} else {
			for(Follow follow : follows) {
				FollowReadResponseDto dto = FollowReadResponseDto.builder()
						.sellerId(follow.getSellerId().getId())
						.companyName(follow.getSellerId().getCompanyName())
						.followCnt(follow.getSellerId().getFollowCnt())
						.build();
				list.add(dto);
			}
		}
		return list;
	}
	
	//4.언팔로우 하기 
	@Transactional
	public void removeStoreFollow(String sId ,Authentication user) {
		String userId = user.getName();
		Optional<Follow> follow = fr.findByUserIdAndSellerId(userId, sId);
		if(follow.isEmpty()) {
			log.info("팔로우 하지 않은 셀러입니다.");
		} else {
			fr.deleteFollowByUserIdAndSellerId(userId, sId);			
		}
	}
	
	//팔로우 여부 확인
	@Transactional
	public int getFollowCheck(String sId,Authentication user) {
		String userId = user.getName();
		Optional<Follow> follow = fr.findByUserIdAndSellerId(userId,sId);
		if(follow.isEmpty()) {
			return 0;  //팔로우 X
		}
		else {
			return 1;  //이미 팔로우 함
		}
	}
}


