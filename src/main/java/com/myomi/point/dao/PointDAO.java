package com.myomi.point.dao;

import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;

public interface PointDAO {
	public List<Map<String, Object>> selectPoint(String userId) throws FindException;
}
