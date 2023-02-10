package moonrise.pjt3.debate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.entity.Message;
import moonrise.pjt3.debate.service.DebateService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController @Log4j2
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final DebateService debateService;
    @MessageMapping(value = "/chat/enter") //채팅방 입장
    public void enter(Long debateId, String writer){
        DebateChatDto debateChatDto = DebateChatDto.builder()
                .content(writer +"님이 채팅방에 입장했습니다.")
                .writer("[알림]") // 채팅방 입장 시 다른 참가자들에게 안내
                .build();
        log.info("[알림] " + writer + "님이 채팅방에 입장했습니다.");
        template.convertAndSend("/sub/chat/room/" + debateId, debateChatDto);
    }

    @MessageMapping(value = "/chat/message") // 채팅 메시지 들어옴
    public void message(DebateChatDto debateChatDto){
        System.out.println(debateChatDto);
        debateService.saveCacheChat(debateChatDto);
        template.convertAndSend("/sub/chat/room/" + debateChatDto.getDebateId(), debateChatDto);
    }
}
