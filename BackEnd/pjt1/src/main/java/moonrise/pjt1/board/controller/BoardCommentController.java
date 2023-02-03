package moonrise.pjt1.board.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.board.dto.BoardCommentUpdateDto;
import moonrise.pjt1.board.service.BoardCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/comments")
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    // 댓글, 대댓글 작성 (0순위)
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> commentWrite(@RequestBody BoardCommentCreateDto boardCommentCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long boardCommentId = boardCommentService.createComment(boardCommentCreateDto);
        result.put("boardCommentId",boardCommentId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    // 댓글 (상세보기) -> 필요없을듯


    // 댓글 좋아요 (1순위)


    // 댓글 수정 (1순위)
    @PostMapping("/modify")
    public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody BoardCommentUpdateDto boardCommentUpdateDto){
        Map<String, Object> result = new HashMap<>();
        Long commentId = boardCommentService.updateComment(boardCommentUpdateDto);
        result.put("commentId", commentId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 댓글 상태(삭제, 신고, 평범) (1순위)
    // 1:normal 2: banned 3: deleted
    @PostMapping("/status")
    public ResponseEntity<String> boardCommentChangeStatus(@RequestParam(name="commentId") Long commentId, @RequestParam(name="statusCode") int statusCode){
        boardCommentService.statusComment(commentId, statusCode);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 상태가 변경되었습니다");
    }
}
