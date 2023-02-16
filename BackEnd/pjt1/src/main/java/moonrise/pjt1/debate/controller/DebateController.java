package moonrise.pjt1.debate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.debate.dto.DebateCreateDto;
import moonrise.pjt1.debate.service.DebateService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/debate")
@RequiredArgsConstructor @Log4j2
public class DebateController {
    private final DebateService debateService;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "movieId") Long movieId,
                                                    @RequestParam(value = "page", defaultValue = "0") int page){
        PageRequest pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        ResponseDto responseDto = debateService.listDebate(movieId, pageable);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/create") // 채팅방 생성
    public ResponseEntity<?> createDebate(@RequestHeader HttpHeaders headers, @RequestBody DebateCreateDto debateCreateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = debateService.createDebate(access_token, debateCreateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/read/{debateId}") // 채팅방 상세보기
    public ResponseEntity<?> readDebate(@RequestHeader HttpHeaders headers, @PathVariable Long debateId){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = debateService.readDebate(access_token, debateId);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
