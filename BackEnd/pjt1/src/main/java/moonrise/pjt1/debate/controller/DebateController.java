package moonrise.pjt1.debate.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.debate.dto.DebateCreateDto;
import moonrise.pjt1.debate.service.DebateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/debate")
@RequiredArgsConstructor
public class DebateController {
    private final DebateService debateService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listDebate(@RequestParam(value = "movieId") Long movieId){
        Map<String, Object> result = debateService.listDebate(movieId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/create") // 채팅방 생성
    public ResponseEntity<Map<String, Object>> createDebate(@RequestBody DebateCreateDto debateCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long debateId = debateService.createParty(debateCreateDto);
        result.put("debateId",debateId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }

    @GetMapping("/read/{debateId}") // 채팅방 상세보기
    public ResponseEntity<Map<String, Object>> readDebate(@PathVariable Long debateId){
        Map<String, Object> result = debateService.readDebate(debateId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
}
