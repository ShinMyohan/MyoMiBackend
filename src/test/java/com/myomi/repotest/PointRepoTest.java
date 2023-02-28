package com.myomi.repotest;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myomi.point.entity.Point;
import com.myomi.point.entity.PointDetail;
import com.myomi.point.entity.PointDetailEmbedded;
import com.myomi.point.repository.PointDetailRepository;
import com.myomi.point.repository.PointRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
class PointRepoTest {
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private PointRepository pr;

	@Autowired
	private PointDetailRepository pdr;

	@Autowired
	private UserRepository ur;
	
	@Test
	//point_detail 넣으려면 point테이블에 해당 아이디의 컬럼이 있어야함 !
	void testPoint() {
		Optional<User> optU = ur.findById("id1");
		Point point = new Point();
		User user = optU.get();
		point.setTotalPoint(0);
		point.setId(optU.get().getId());
		point.setUserId(user); //중요! point 쪽에서도, user 객체 넣어준뒤 setter => 왜냐하면 양방향이니까!
		
		pr.save(point);
	}

	@Test
	void testSavePoint() {
		Optional<User> optU = ur.findById("id1");
		Point point = optU.get().getPoint(); //토탈 포인트
        
		PointDetail pd = new PointDetail();
		PointDetailEmbedded pde = new PointDetailEmbedded();
		pde.setUId(optU.get().getId());
		LocalDateTime date = LocalDateTime.now();
		pde.setCreatedDate(date);
		pd.setPointEmbedded(pde);
		pd.setSort(0);
		pd.setAmount(-1000); //포인트 사용했다는 가정하에 그냥 음수넣으면 db에 토탈포인트 -됨
		Integer amt = pd.getAmount();
		point.setTotalPoint(point.getTotalPoint()+amt);
		point.setUserId(optU.get());
		pd.setPoint(point);

		pr.save(point);
		pdr.save(pd);
	}


	@Test
	void testFindPointAll() {
		Optional<User> optU = ur.findById("id1");
		Iterable<PointDetail> pds= pdr.findAll();
		pds.forEach((pd)->{
			logger.info("[" + optU.get().getName()+"]님의 포인트 적립 내역: " );
			if(pd.getSort() == 0 ) {
				logger.info(pd.getPointEmbedded().getCreatedDate() + "| 회원가입 축하 [" + pd.getAmount() + "]원 적립");
			} else if(pd.getSort() == 1) {
				logger.info(pd.getPointEmbedded().getCreatedDate() + "| 상품 구매 [" + pd.getAmount() + "]원 적립");
			} else {
				logger.info(pd.getPointEmbedded().getCreatedDate() + "| 그 외 [" + pd.getAmount() + "]원 적립");
			}
			logger.info("--------------------------------------------------" );
		});
	}
	
	
	
}