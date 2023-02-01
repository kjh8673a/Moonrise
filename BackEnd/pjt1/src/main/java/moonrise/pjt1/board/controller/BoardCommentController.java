package moonrise.pjt1.board.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.board.service.BoardCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/comments")
public class BoardCommentController {
    private final BoardCommentService boardCommentService;
//    private final
    // 댓글 목록 (0순위) -> BoardDetail 에서 목록만 추가로 넘기기

    // 댓글 작성 (0순위)
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> writeComment(@RequestBody BoardCommentCreateDto boardCommentCreateDto){
        Map<String, Object> result = new HashMap<>();
        Long boardCommentId = boardCommentService.createComment(boardCommentCreateDto);
        result.put("boardCommentId",boardCommentId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
    }

    // 댓글 (상세보기) ??

    // 대댓글 작성

    // 댓글 삭제 (1순위)

    // 댓글 좋아요 (1순위)
    // 댓글 수정 (1순위)
    //@PostMapping("/modify")
    // 댓글 상태 (1순위)
}
