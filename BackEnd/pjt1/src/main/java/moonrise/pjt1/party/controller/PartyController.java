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
    public ResponseEntity<?> read(@RequestHeader HttpHeaders headers, @PathVariable Long partyId){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.readParty(access_token, partyId);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/list") // 파티 목록 조회
    public ResponseEntity<?> list(@RequestParam(value = "movieId") Long movieId,
                                  @RequestParam(value = "page", defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        ResponseDto responseDto = partyService.listParty(movieId, pageable);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/write") // 파티 생성
    public ResponseEntity<?> createParty(@RequestHeader HttpHeaders headers, @RequestBody PartyCreateDto partyCreateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.createParty(access_token, partyCreateDto);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/comment/write") // 댓글, 대댓글 작성
    public ResponseEntity<?> writeComment(@RequestHeader HttpHeaders headers, @RequestBody PartyCommentCreateDto partyCommentCreateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.createComment(access_token, partyCommentCreateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    // 댓글 수정
    @PostMapping("/comment/modify")
    public ResponseEntity<?> updateComment(@RequestHeader HttpHeaders headers, @RequestBody PartyCommentUpdateDto partyCommentUpdateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.updateComment(access_token,partyCommentUpdateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    // 댓글 상태(삭제, 신고, 평범) (1순위)
    // 1:normal 2: banned 3: deleted
    @PostMapping("/comment/status")
    public ResponseEntity<?> commentChangeStatus(@RequestHeader HttpHeaders headers, @RequestParam(name="commentId") Long commentId, @RequestParam(name="statusCode") int statusCode){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.statusComment(access_token,commentId, statusCode);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/join") // 소모임 참가 신청
    public ResponseEntity<?> writeJoin(@RequestHeader HttpHeaders headers, @RequestBody PartyJoinCreateDto partyJoinCreateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.createJoin(access_token,partyJoinCreateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    // 소모임 상태
    // 1:모집완료 2: 기간만료 3: 삭제
    @GetMapping("/status") //소모임 상태변경
    public ResponseEntity<?> updatePartyStatus(@RequestHeader HttpHeaders headers, @RequestParam(value = "partyId") Long partyId,
                                                                @RequestParam(value = "status") int status){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.updatePartyStatus(access_token,partyId, status);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    // 소모임 참가신청 상태
    // 1:승인 2: 거절 3: 취소
    @GetMapping("/join/status") //소모임 신청 상태변경
    public ResponseEntity<?> updateJoinStatus(@RequestHeader HttpHeaders headers, @RequestParam(value = "joinId") Long joinId,
                                                                @RequestParam(value = "status") int status){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.updateJoinStatus(access_token,joinId, status);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyParty(@RequestHeader HttpHeaders headers, @RequestBody PartyModifyDto partyModifyDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.modifyParty(access_token,partyModifyDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/join/list")
    public ResponseEntity<?> listPartyJoin(@RequestHeader HttpHeaders headers){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.listPartyJoin(access_token);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/list/my")
    public ResponseEntity<?> listMyParty(@RequestHeader HttpHeaders headers){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = partyService.listMyParty(access_token);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

}
