package com.myomi.point.service;

import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.point.dao.PointDAO;
import com.myomi.point.dao.PointDAOOracle;

public class PointService {
	PointDAO dao = new PointDAOOracle();
	
	public List<Map<String, Object>> findPoint(String userId) throws FindException {
	return dao.selectPoint(userId);
	}
	}
