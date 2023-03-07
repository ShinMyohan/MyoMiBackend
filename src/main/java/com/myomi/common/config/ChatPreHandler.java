package com.myomi.common.config;

import com.myomi.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor { // 메시지가 서버에 도착해서 발행되기 전에 동작하는 인터셉터

    private final JwtTokenProvider jwtTokenProvider;
    private static final String BEARER_PREFIX = "Bearer ";


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String bearerToken = accessor.getFirstNativeHeader("Authorization");
//        System.out.println("bearerToken : " + bearerToken);
//        String token = bearerToken.substring(BEARER_PREFIX.length());

        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(!jwtTokenProvider.validateToken(bearerToken))
                throw new AccessDeniedException("token");
        }
        return message;
    }
}
