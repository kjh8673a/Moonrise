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
    @PostMapping("/testtesttest")
    public void message(@RequestBody DebateChatDto debateChatDto){
        System.out.println(debateChatDto);
        debateService.saveCacheChat(debateChatDto);
    }
    @GetMapping("/pastChats") // 채팅방 입장 시 redis서버 채팅내역 리턴
    public ResponseEntity<?> enterDebateRoom(@RequestParam(value = "debateId") Long debateId,
                                             @RequestParam(value = "findCnt",defaultValue = "0") int findCnt) throws JsonProcessingException {
        log.info(debateId);
        if(findCnt == 0){ // 0이면 채팅방 첫 입장임
            boolean isMax = debateService.updateLivePeopleCnt(debateId);
            if(!isMax){
                ResponseDto responseDto = new ResponseDto();
                responseDto.setMessage("참가인원이 꽉찼습니다.");
                responseDto.setData(null);
                responseDto.setStatus_code(400);
                return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
            }
        }
        ResponseDto responseDto = debateService.getRecentChat(debateId, findCnt);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

}
