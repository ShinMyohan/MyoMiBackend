package com.myomi.seller.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.seller.entity.Seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter@NoArgsConstructor
public class SellerDetailDto {

   private String sellerId;
   private String sellerName;
   private String companyName;
   private String companyNum;
   private String internetNum;
   private String addr;
   private String manager;
   private String bankAccount;
   private int status;
   @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
   private Date signoutDate;
   @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
   private LocalDateTime createdDate;
   private String email;
   private String companyImgUrl;
   private String internetImgUrl;
   private String phoneNum;

   public SellerDetailDto(Seller entity) {
      this.sellerName = entity.getSellerId().getName();
      this.sellerId = entity.getSellerId().getId();
      this.companyName = entity.getCompanyName();
      this.companyNum = entity.getCompanyNum();
      this.internetNum = entity.getInternetNum();
      this.addr = entity.getAddr();
      this.manager = entity.getManager();
      this.bankAccount = entity.getBankAccount();
      this.status = entity.getStatus();
      this.signoutDate=entity.getSellerId().getSignoutDate();
      this.createdDate=entity.getSellerId().getCreatedDate();
      this.email=entity.getSellerId().getEmail();
      this.internetImgUrl=entity.getInternetImgUrl();
      this.companyImgUrl=entity.getCompanyImgUrl();
      this.phoneNum = entity.getSellerId().getTel();
   }
}