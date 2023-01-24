package com.myomi.membership.service;

import java.util.List;

import com.myomi.exception.FindException;
import com.myomi.membership.dao.MembershipDAO;
import com.myomi.membership.dao.MembershipDAOOracle;
import com.myomi.membership.vo.MembershipVo;

public class MembershipService {

	public List<MembershipVo> findAllMembership() throws FindException{
	    MembershipDAO dao = new MembershipDAOOracle(); //다형성 
		return dao.selectAll();
	}
	
	public static void main(String[] args) throws FindException{
		MembershipDAOOracle dao = new MembershipDAOOracle();
       
		dao.selectAll();
	
	}
	
}
