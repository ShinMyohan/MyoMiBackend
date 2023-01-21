package com.myomi.product.service;

import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.product.dao.ProductDAO;
import com.myomi.product.dao.ProductDAOOracle;
import com.myomi.product.vo.ProductVo;

public class ProductService {
	ProductDAO pDao = new ProductDAOOracle();
	
	public List<Map<String, Object>> findAllProduct() throws FindException {
		return pDao.selectAllProduct();
	}
	
	public List<Map<String, Object>> findProductByCategory(String category) throws FindException {
		return pDao.selectProductByCategory(category);
	}
	
	public List<Map<String, Object>> infoOneProduct(int num) throws FindException {
		return pDao.selectOneProduct(num);
	}
	
	public void addProduct(ProductVo pVo) throws FindException {
		pDao.insertProduct(pVo);
	}
	
	public void modifyProduct(ProductVo pVo) throws FindException {
		pDao.updateProduct(pVo);
	}
	
//	public static void main(String[] args) throws FindException {
//		ProductService pService = new ProductService();
//		ProductVo pVo = new ProductVo();
//		SellerVo sVo = new SellerVo();
//		UserVo uVo = new UserVo();
//		uVo.setId("user3"); //나중에 세션에 들어감
//		sVo.setUser(uVo);
//		pVo.setSeller(sVo);
//		pVo.setCategory("샐러드");
//		pVo.setName("스리라차 고등어 샐러드");
//		pVo.setOriginPrice(72000);
//		pVo.setPercentage(25);
//		pVo.setWeek(4);
//		pVo.setStatus(0);
//		pVo.setDetail("");
//		pVo.setFee(9);
//		
//		pService.addProduct(pVo);
//	}
}
