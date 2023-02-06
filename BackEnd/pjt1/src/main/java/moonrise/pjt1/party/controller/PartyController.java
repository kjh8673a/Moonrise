package moonrise.pjt1.party.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.party.dto.*;
import moonrise.pjt1.party.entity.Party;
import moonrise.pjt1.party.service.PartyService;
import moonrise.pjt1.util.HttpUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
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
        Map<String, Object> result = partyService.readParty(partyId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @GetMapping("/list") // 파티 목록 조회
    public ResponseEntity<Map<String, Object>> list(@RequestParam(value = "movieId") Long movieId,
                                                    @RequestParam(value = "page", defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Map<String, Object> result = partyService.listParty(movieId, pageable);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    @PostMapping("/write") // 파티 생성
    public ResponseEntity<?> createParty(@RequestHeader HttpHeaders headers, @RequestBody PartyCreateDto partyCreateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);

        ResponseDto responseDto = partyService.createParty(access_token, partyCreateDto);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.ACCEPTED);
    }
    @PostMapping("/comment/write") // 댓글, 대댓글 작성
    public ResponseEntity<Map<String, Object>> writeComment(@RequestBody PartyCommentCreateDto partyCommentCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long partyCommentId = partyService.createComment(partyCommentCreateDto);
        result.put("partyCommentId",partyCommentId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
    // 댓글 수정
    @PostMapping("/comment/modify")
    public ResponseEntity<Map<String, Object>> updateComment(@RequestBody PartyCommentUpdateDto partyCommentUpdateDto){
        Map<String, Object> result = new HashMap<>();
        Long commentId = partyService.updateComment(partyCommentUpdateDto);
        result.put("partyCommentId", commentId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 댓글 상태(삭제, 신고, 평범) (1순위)
    // 1:normal 2: banned 3: deleted
    @PostMapping("/comment/status")
    public ResponseEntity<String> commentChangeStatus(@RequestParam(name="commentId") Long commentId, @RequestParam(name="statusCode") int statusCode){
        partyService.statusComment(commentId, statusCode);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 상태가 변경되었습니다");
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

    @PutMapping("/modify")
    public ResponseEntity<Map<String, Object>> modifyParty(@RequestBody PartyModifyDto partyModifyDto){
        Map<String, Object> result = new HashMap<>();
        Party party = partyService.modifyParty(partyModifyDto);
        result.put("party",party);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.ACCEPTED);
    }
}
