package com.myomi.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myomi.admin.dto.SellerDetailDto;
import com.myomi.admin.dto.SellerResponseDto;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class AdminService {
	private final SellerRepository sr;

public List<SellerResponseDto> findSeller(){
	List<Seller> sellers = sr.findAll();
	List<SellerResponseDto>list = new ArrayList<>();
	 
		for(Seller seller:sellers) {
			SellerResponseDto dto = SellerResponseDto.builder()
									.sellerId(seller.getId())
									.companyName(seller.getCompanyName())
									.status(seller.getStatus())
									.signoutDate(seller.getSellerId().getSignoutDate())
									.build();
									list.add(dto);
									
		
									System.out.println(list);
	}
	return list;
}

public List<SellerResponseDto> findSellerListByStatus(int status){
	List<Seller> sellers=sr.findAllByStatus(status);
	List<SellerResponseDto>list = new ArrayList<>();
	for(Seller seller:sellers) {
		SellerResponseDto dto = SellerResponseDto.builder()
								.sellerId(seller.getId())
								.companyName(seller.getCompanyName())
								.status(seller.getStatus())
								.signoutDate(seller.getSellerId().getSignoutDate())
								.build();
								list.add(dto);
								
	
								System.out.println(list);
}
		return list;
}
	//seller정보 상세보기
	public SellerDetailDto detailSellerInfo(String sellerId) {
		Seller seller = sr.findSellerBySellerId(sellerId);
		System.out.println(seller);
		return new SellerDetailDto(seller);
	}
}
