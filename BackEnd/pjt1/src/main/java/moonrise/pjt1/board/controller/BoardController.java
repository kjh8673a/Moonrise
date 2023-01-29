package moonrise.pjt1.board.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    // 게시글 목록보기
    @GetMapping(value = "/list/{movieId}")
    public ResponseEntity<Map<String, Object>>boardList(@PathVariable("movieId") Long movieId){
        Map<String, Object> result = boardService.listBoard(movieId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 게시글 상세보기
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> boardDetail(@PathVariable("boardId") Long boardId){
        Map<String, Object> result = boardService.detailBoard(boardId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 게시글 생성
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> boardCreate(@RequestBody BoardForm boardForm){

    }
    // 게시글 수정
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> boardUpdate(@RequestBody BoardForm boardForm){
        return
    }

// 게시글 삭제 -> 글 상태바꾸기
}
