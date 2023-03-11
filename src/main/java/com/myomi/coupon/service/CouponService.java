package com.myomi.coupon.service;

import com.myomi.coupon.dto.CouponDto;
import com.myomi.coupon.entity.Coupon;
import com.myomi.coupon.repository.CouponRepository;
import com.myomi.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class CouponService {
    private final CouponRepository cr;
    private final UserRepository ur;

    //마이페이지에서 쿠폰 조회
    @Transactional
    public List<CouponDto> findCouponList(Authentication user, Pageable pageable) {
        LocalDateTime date = LocalDateTime.now();
        String username = user.getName();
        List<Coupon> list = cr.findAllByUser(username, pageable);
        List<CouponDto> couponList = new ArrayList<>();
        for (Coupon c : list) {
            CouponDto dto = CouponDto.builder()
                    .userId(username)
                    .couponNum(c.getCouponNum())
                    .sort(c.getSort())
                    .percentage(c.getPercentage())
                    .createdDate(date)
                    .status(c.getStatus())
                    .usedDate(c.getUsedDate())
                    .build();
            couponList.add(dto);
        }
        return couponList;
    }

    //쿠폰 사용시 status update
    @Transactional
    public void modifyCoupon(Long couponNum, int status) {
        //        LocalDateTime date = LocalDateTime.now();
        Optional<Coupon> cp = cr.findById(couponNum);
        Coupon coupon = cp.get();
        if (status == 1 && coupon.getStatus() == 0) {  // 결제할 때
            coupon.update(couponNum, status);
        } else if (status == 0 && coupon.getStatus() == 1) { // 결제 취소할 때
            if (LocalDateTime.now().isBefore(coupon.getCreatedDate().plusDays(30))) {
                coupon.update(couponNum, status);
            } else {
                status = 2;
                coupon.update(couponNum, status); // 만료 된 상태로 바꾸기 -> 사용일은 있으므로 null값이 사용기준이 될 순 없음
                log.info("결제 취소했으나, 쿠폰 만료기간이 지나서 사용할 수 없음");
            }
        }
    }

    @Transactional
    public Long getTotalCoupon(Authentication user) {
        String username = user.getName();
        Long count = cr.findByUser(username);
        return count;
    }
}
