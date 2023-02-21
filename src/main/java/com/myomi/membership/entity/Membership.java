package com.myomi.membership.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Membership {
    @Id
    @Column(name = "membership_num")
    private Long mNum;

    @Column(name = "membership_level", nullable = false)
    private String mLevel;
}
