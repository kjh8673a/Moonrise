package moonrise.pjt1.party.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.party.dto.PartyCommentCreateDto;
import moonrise.pjt1.party.dto.PartyCreateDto;
import moonrise.pjt1.party.dto.PartyJoinCreateDto;
import moonrise.pjt1.party.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/party")
@Log4j2
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;
    @GetMapping("/read/{partyId}") // 파티 상세보기
    public ResponseEntity<Map<String, Object>> read(@PathVariable Long partyId){
        System.out.println(partyId);
        Map<String, Object> result = partyService.readParty(partyId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @GetMapping("/list") // 파티 목록 조회
    public ResponseEntity<Map<String, Object>> list(@RequestParam(value = "movieId") Long movieId){
        System.out.println(movieId);
        Map<String, Object> result = partyService.listParty(movieId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @PostMapping("/write") // 파티 생성
    public ResponseEntity<Map<String, Object>> createParty(@RequestBody PartyCreateDto partyCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long partyId = partyService.createParty(partyCreateDto);
        result.put("partyId",partyId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @PostMapping("/comment/write") // 댓글 작성
    public ResponseEntity<Map<String, Object>> writeComment(@RequestBody PartyCommentCreateDto partyCommentCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long partyCommentId = partyService.createComment(partyCommentCreateDto);
        result.put("partyCommentId",partyCommentId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @PostMapping("/join") // 소모임 참가 신청
    public ResponseEntity<Map<String, Object>> writeJoin(@RequestBody PartyJoinCreateDto partyJoinCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long joinId = partyService.createJoin(partyJoinCreateDto);
        result.put("partyJoinId",joinId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @GetMapping("/status") //소모임 신청 상태변경
    public ResponseEntity<Map<String, Object>> updatePartyStatus(@RequestParam(value = "partyId") Long partyId,
                                                                @RequestParam(value = "status") int status){
        Map<String, Object> result = new HashMap<>();
        partyService.updatePartyStatus(partyId, status);
        result.put("partyId",partyId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @GetMapping("/join/status") //소모임 신청 상태변경
    public ResponseEntity<Map<String, Object>> updateJoinStatus(@RequestParam(value = "joinId") Long joinId,
                                                                @RequestParam(value = "status") int status){
        Map<String, Object> result = new HashMap<>();
        partyService.updateJoinStatus(joinId, status);
        result.put("partyJoinId",joinId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
}
