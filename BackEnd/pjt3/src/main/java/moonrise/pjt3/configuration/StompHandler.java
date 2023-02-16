package moonrise.pjt3.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.debate.service.DebateService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component @Log4j2
@RequiredArgsConstructor
public class StompHandler extends ChannelInterceptorAdapter {
    private final DebateService debateService;

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        switch (accessor.getCommand()) {
            case CONNECT:
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                log.info("*******채팅방 입장******* sessionId : "+sessionId);
                log.info(message);
                log.info(accessor);
                log.info(accessor.getMessageHeaders());
                break;
            case DISCONNECT:
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
                log.info("session연결 끊김 sessionId : "+sessionId);
                log.info(message);
                log.info(accessor);
                log.info(accessor.getMessageHeaders());
                break;
            default:
                break;
        }

    }
}