package com.myomi.seller.repository;

import com.myomi.order.entity.OrderDetail;
import com.myomi.product.entity.Product;
import com.myomi.seller.entity.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends CrudRepository<Seller, String>{
	public List<Seller> findAll();
	
	//판매자 사업자등록번호 검증
	@Query("SELECT s FROM Seller s WHERE s.companyNum =:num")
	Optional<Seller> findByCompanyNum(@Param("num")String num);

	//주문별 조회
	@Query("SELECT p FROM Product p WHERE p.seller.sellerId.id =:userId")
	List<Product> findAllBySellerId(@Param("userId")String userId);
	
	///주문별 상세조회
	@Query("SELECT od FROM OrderDetail od WHERE od.order.orderNum = :orderNum AND od.product.prodNum =:prodNum")
	Optional<OrderDetail> findByOrder(@Param("orderNum")Long orderNum, @Param("prodNum")Long prodNum);

	//판매자 탈퇴신청(status 변경)
	//판매자 승인 혹은 반려(status 변경)
	@Modifying
	@Transactional
	@Query("UPDATE Seller s SET s.status=:status WHERE s.sellerId.id=:userId")
	public void updateSellerId(@Param("status") int status, @Param("userId") String userId);

	//특정 셀러 찾기
	Optional<Seller> findById(String sellerId);
	
	//판매자 상품조회
	@Query("SELECT p FROM Product p WHERE p.seller.sellerId.id =:userId")
	List<Product> findProductBySellerId(@Param("userId")String userId);	
}

