package com.myomi.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class SocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatPreHandler chatPreHandler;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")
                .setAllowedOrigins("http://192.168.35.238:5500")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");  // publish, = topic 1:N, client에서 SEND 요청을 처리한다.
        registry.enableSimpleBroker("/sub"); // subscribe = queue 1:1, enableSimpleBroker: 해당 경로로 SimpleBroker 등록
    }

    // interceptor
    @Order(Ordered.HIGHEST_PRECEDENCE + 99) // spring security보다 우선순위
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(chatPreHandler);
    }
}