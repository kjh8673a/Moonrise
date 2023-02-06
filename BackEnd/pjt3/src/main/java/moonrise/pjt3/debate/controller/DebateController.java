package moonrise.pjt3.debate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.dto.DebateCreateDto;
import moonrise.pjt3.debate.service.DebateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/debate")
@RequiredArgsConstructor @Log4j2
public class DebateController {

    private final DebateService debateService;
    @PostMapping("/testtesttest")
    public void message(@RequestBody DebateChatDto debateChatDto){
        System.out.println(debateChatDto);
        debateService.saveCacheChat(debateChatDto);

    }
    @GetMapping("/enter") // 채팅방 입장 시 redis서버 채팅내역 리턴
    public ResponseEntity<Map<String, Object>> enterDebateRoom(@RequestParam(value = "debateId") Long debateId) throws JsonProcessingException {
        log.info(debateId);
        Map<String, Object> result = debateService.getRecentChat(debateId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }


}
