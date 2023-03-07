package com.myomi.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.order.entity.OrderDetail;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review")
/*
 * @DynamicInsert
 *
 * @DynamicUpdate
 */
@SequenceGenerator(name = "REVIEW_SEQ_GENERATOR", sequenceName = "REVIEW_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)
public class Review {
    @Id
    @Column(name = "num")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
    private Long reviewNum;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "sort", updatable = false)
    private int sort;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "stars", updatable = false)
    private float stars;

    @OneToOne
    @JoinColumns({@JoinColumn(name = "order_num", updatable = false),
            @JoinColumn(name = "prod_num", updatable = false)})
    @JsonIgnore
    private OrderDetail orderDetail;

    @OneToOne(mappedBy = "review")
    private BestReview bestReview;
    
    private String reviewImgUrl;
    
    @Builder
    public Review(Long reviewNum, User user, int sort, @NotNull String title, @NotNull String content, LocalDateTime createdDate, float stars, OrderDetail orderDetail
    		,String reviewImgurl) {
        this.reviewNum = reviewNum;
        this.user = user;
        this.sort = sort;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.stars = stars;
        this.orderDetail = orderDetail;
        this.reviewImgUrl=reviewImgurl;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}