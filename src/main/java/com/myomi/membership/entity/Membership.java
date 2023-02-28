package com.myomi.membership.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Membership {
    @Id
    @Column(name = "membership_num")
    private int mNum;

    @Column(name = "membership_level", nullable = false)
    private String mLevel;
}
