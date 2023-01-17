package com.myomi.point.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PointVo {
	private String user_id;
	private int totalPoint;	
	private List<PointDetailVo> pointDetail;
}
