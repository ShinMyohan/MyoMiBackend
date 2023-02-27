package com.myomi.point.service;

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

import com.myomi.point.dto.PointDetailDto;
import com.myomi.point.dto.PointDto;
import com.myomi.point.entity.Point;
import com.myomi.point.entity.PointDetail;
import com.myomi.point.entity.PointDetailEmbedded;
import com.myomi.point.repository.PointDetailRepository;
import com.myomi.point.repository.PointRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class PointDetailService {
	private final PointDetailRepository pdr;
	private final PointRepository pr;
	private final UserRepository ur;


	//마이페이지에서 나의 포인트 리스트 조회 
	@Transactional
	public List<PointDetailDto> findMyPointList(Authentication user,
			Pageable pageable) {
		String username = user.getName();
		List<PointDetail> list = pdr.findAllByUser(username, pageable);
		List<PointDetailDto> pointList = new ArrayList<>();
		for (PointDetail pd : list) {
			PointDetailDto pDto = PointDetailDto.builder()
					.id(username)
					.createdDate(pd.getPointEmbedded().getCreatedDate())
					.sort(pd.getSort())
					.amount(pd.getAmount())
					.totalPoint(pd.getPoint().getTotalPoint())
					.build();
			pointList.add(pDto);
		}
		return pointList;
	}
	
	//포인트 적립
	@Transactional
	public ResponseEntity<PointDetailDto> addPoint (PointDetailDto pDto, Authentication user){
		LocalDateTime date = LocalDateTime.now();
		String username = user.getName();
		Optional<User> optU = ur.findById(username);
	    PointDetail pd = new PointDetail();
	    PointDetailEmbedded pde = new PointDetailEmbedded();
	    pde = PointDetailEmbedded.builder()
	    		.createdDate(date)
	    		.uId(username)
	    		.build();
	    pd = PointDetail.builder()
	    		.amount(pDto.getAmount())
	    		.sort(pDto.getSort())
	    		.pointEmbedded(pde)
	    		.build();
	 
	    pdr.save(pd);
	    return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//총 보유 포인트
	@Transactional
	public PointDto findTotalPoint (Authentication user) {
		String username = user.getName();
		Point point = pr.findAllById(username);
	  
		PointDto dto = PointDto.builder()
				.id(username)
				.totalPoint(point.getTotalPoint())
				.build();
		return dto;
		
	}
	
}
