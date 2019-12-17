package com.njust.zcw.hardwareserver.config;

import com.njust.zcw.hardwareserver.utils.socket.MyWebSocketHandler;
import com.njust.zcw.hardwareserver.utils.socket.MyWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * <p>Title: WebSocketConfig</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019版权</p>
 * <p>Company: Chan's Workshop</p>
 *
 * @author Chen Sicong
 * @version V1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig{


//    private final TextWebSocketHandler textWebSocketHandler = new MyWebSocketHandler();
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        registry.addHandler(textWebSocketHandler, "/websocket/socketServer")
//        .addInterceptors(new MyWebSocketHandlerInterceptor()).setAllowedOrigins("*");
//    }
}
