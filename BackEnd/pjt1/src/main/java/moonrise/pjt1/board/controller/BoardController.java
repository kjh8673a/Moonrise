package moonrise.pjt1.board.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardDto;
import moonrise.pjt1.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    // 게시글 목록보기 (0순위)
    @GetMapping(value = "/list/{movieId}")
    public ResponseEntity<Map<String, Object>>boardList(@PathVariable("movieId") Long movieId){
        Map<String, Object> result = boardService.listBoard(movieId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 게시글 상세보기 (0순위)
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> boardDetail(@PathVariable("boardId") Long boardId){
        Map<String, Object> result = boardService.detailBoard(boardId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 게시글 생성 (0순위)
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> boardCreate(@RequestBody BoardDto boardDto){
        System.out.println("boardDto = " + boardDto.getTitle());
        Map<String, Object> result = new HashMap<>();
        Long boardId = boardService.createBoard(boardDto);
        result.put("board", boardDto);
        result.put("boardId", boardId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);

    }
    // 게시글 수정 (0순위)
//    @PutMapping("")
//    public ResponseEntity<Map<String, Object>> boardUpdate(@RequestBody BoardForm boardForm){
//
//    }

    // 게시글 삭제 (0순위)-> 글 상태바꾸기
    // 게시글 인기목록 (1순위)
    // 게시글 좋아요 (1순위)
    // 게시글 북마크 (1순위)
    // 게시글 신고 (2순위)

}
