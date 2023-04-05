package com.myomi.qna.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SequenceGenerator(
        name =
                "QNA_SEQ_GENERATOR", // 사용할 sequence 이름
        sequenceName =
                "QNA_SEQ", // 실제 데이터베이스 sequence 이름
        initialValue = 1, allocationSize = 1)

@Entity //엔티티 객체
@Table(name = "qna") //어떤 테이블과 영속성을 유지할지
@DynamicInsert
@DynamicUpdate

public class Qna implements Serializable {
    @Id
    @Column(name = "num")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator =
                    "QNA_SEQ_GENERATOR") // 위의 sequence 이름
    private Long qnaNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "prod_num", nullable = false)
    @NotNull
    private Product prodNum;

    @Column(name = "que_title")
    @NotNull
    private String queTitle;

    @Column(name = "que_content")
    @NotNull
    private String queContent;

    @Column(name = "que_created_date", updatable = false)
    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime queCreatedDate;

    @Column(name = "ans_content")
    private String ansContent;

    @Column(name = "ans_created_date")
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime ansCreatedDate;
    
    private String qnaImgUrl;
    
    @Builder
    public Qna(Long qnaNum, User userId, Product prodNum, String queTitle,
               String queContent, LocalDateTime queCreatedDate, String ansContent,
               LocalDateTime ansCreatedDate, String qnaImgUrl) {
        this.qnaNum = qnaNum;
        this.userId = userId;
        this.prodNum = prodNum;
        this.queTitle = queTitle;
        this.queContent = queContent;
        this.queCreatedDate = queCreatedDate;
        this.ansContent = ansContent;
        this.ansCreatedDate = ansCreatedDate;
        this.qnaImgUrl = qnaImgUrl;
    }

    public void update(String queTitle, String queContent) {
        this.queTitle = queTitle;
        this.queContent = queContent;
    }

    public void updateAns(String ansContent, LocalDateTime ansCreatedDate) {
        this.ansContent = ansContent;
        this.ansCreatedDate = ansCreatedDate;
    }
    
    public void update(String qnaImgUrl) {
    	this.qnaImgUrl = qnaImgUrl;
    }
}
