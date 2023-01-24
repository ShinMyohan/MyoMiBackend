package com.myomi.membership.dao;

import java.util.List;

import com.myomi.exception.FindException;
import com.myomi.membership.vo.MembershipVo;

public interface MembershipDAO {
	public List<MembershipVo> selectAll() throws FindException; 

}
