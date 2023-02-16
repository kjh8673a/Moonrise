package moonrise.pjt3.debate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.commons.response.ResponseDto;
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

    @GetMapping("/pastChats") // 채팅방 입장 시 redis서버 채팅내역 리턴
    public ResponseEntity<?> enterDebateRoom(@RequestParam(value = "debateId") Long debateId,
                                             @RequestParam(value = "findCnt",defaultValue = "0") int findCnt) throws JsonProcessingException {
        log.info(debateId+"번 채팅방 이전 채팅내역 찾기   findCnt : "+ findCnt);
        ResponseDto responseDto = debateService.getRecentChat(debateId, findCnt);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/exit") // 채팅방 퇴장 시 현재인원 수 값 조정
    public ResponseEntity<?> exitDebateRoom(@RequestParam(value = "debateId") Long debateId) {
        log.info(debateId+"번 채팅방 나가기 요청");
        ResponseDto responseDto = debateService.minusLivePeopleCnt(debateId);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkEnterRoom(@RequestParam(value = "debateId") Long debateId) {
        log.info(debateId+"번 채팅방 입장가능 여부 체크");
        ResponseDto responseDto = debateService.checkEnter(debateId);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
