package com.myomi.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.myomi.user.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "num")
    private Long num;
	//private Seller seller;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "origin_price")
    private Long originPrice;

    @Column(name = "percentage")
    private int percentage;

    @Column(name = "week")
    private int week;

    @Column(name = "status")
    private int status;

    @Column(name = "detail")
    private String detail;

    @Column(name = "review_cnt")
    private Long reviewCnt;

    @Column(name = "stars")
    private int stars;

    @Column(name = "fee")
    @ColumnDefault("9") //default 9
    private int fee;
//	private List<Qna> qnas;
//	private List<Review> reviews;
    
    //@OneToMany(cascade=CascadeType.ALL,
   // 		mappedBy="product")
  
}
