package moonrise.pjt1.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.board.dto.BoardCommentUpdateDto;
import moonrise.pjt1.board.service.BoardCommentService;
import moonrise.pjt1.commons.response.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/comments")
@Log4j2
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    // 댓글, 대댓글 작성 (0순위)
    @PostMapping("/create")
    public ResponseEntity<?> commentWrite(@RequestHeader HttpHeaders headers,
                                          @RequestBody BoardCommentCreateDto boardCommentCreateDto){

        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardCommentService.createComment(access_token, boardCommentCreateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    // 댓글 좋아요 (1순위)


    // 댓글 수정 (1순위)
    @PostMapping("/modify")
    public ResponseEntity<?> commentUpdate(@RequestHeader HttpHeaders headers,
                                           @RequestBody BoardCommentUpdateDto boardCommentUpdateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardCommentService.updateComment(access_token, boardCommentUpdateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    // 댓글 상태(삭제, 신고, 평범) (1순위)
    // 1:normal 2: banned 3: deleted
    @PostMapping("/status")
    public ResponseEntity<?> boardCommentChangeStatus(@RequestHeader HttpHeaders headers,
                                                           @RequestParam(name="commentId") Long commentId, @RequestParam(name="statusCode") int statusCode){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardCommentService.statusComment(access_token, commentId, statusCode);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
