package com.myomi.membership.service;

import com.myomi.membership.entity.MembershipLevel;
import com.myomi.order.dto.OrderSumResponseDto;
import com.myomi.order.repository.OrderRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Scheduled(cron="0 0 0 1 1,4,7,10 *") // 분기별로 산정 (1,4,7,10월 1일 정각)
    @Transactional
    public void findOrderTotalPrice() {
        MembershipLevel membershipLevel = MembershipLevel.NEW;
        // 멤버십 전체 초기화
        userRepository.updateAllMembership(membershipLevel);

        // 실적에 따라 등급 변경
        List<OrderSumResponseDto> dtoList = orderRepository.findOrderTotalPrice();

        for (OrderSumResponseDto dto : dtoList) {
            String userId = dto.getUserId();
            User user = User.builder().id(userId).build();

            if (dto.getTotalPrice() >= 200000) {
                membershipLevel = MembershipLevel.DIAMOND;
            } else if (dto.getTotalPrice() >= 150000) {
                membershipLevel = MembershipLevel.PLATINUM;
            } else if (dto.getTotalPrice() >= 100000) {
                membershipLevel = MembershipLevel.GOLD;
            }
            userRepository.updateMembershipByUserId(userId, membershipLevel);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        log.info("멤버십 변경 시간: " + strDate);
    }
}
