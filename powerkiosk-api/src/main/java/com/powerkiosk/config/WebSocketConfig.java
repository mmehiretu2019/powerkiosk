package com.powerkiosk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
@EnableWebSocketMessageBroker
@Order(1)//It is very important that the order is set to 1 here so that this configuration is loaded first in the security chain
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer  {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //TODO: remove CORS
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    @Override
    public void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        messages.
                simpTypeMatchers(CONNECT, HEARTBEAT, UNSUBSCRIBE, DISCONNECT).permitAll()
                .simpDestMatchers("/topic/**").authenticated()
                //.simpDestMatchers("/app/**").authenticated()
                .anyMessage().authenticated();
//                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
//                // catch all
//                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
