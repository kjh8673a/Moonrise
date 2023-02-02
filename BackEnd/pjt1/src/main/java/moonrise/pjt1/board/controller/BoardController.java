package moonrise.pjt1.board.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardCreateDto;
import moonrise.pjt1.board.dto.BoardUpdateDto;
import moonrise.pjt1.board.service.BoardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
//    @GetMapping(value = "/list/{movieId}")
//    public ResponseEntity<Map<String, Object>>boardList(@PathVariable("movieId") Long movieId){
//        Map<String, Object> result = boardService.listBoard(movieId);
//        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
//    }
    @GetMapping(value = "/list/{movieId}")
    public ResponseEntity<Map<String, Object>>boardList(@PathVariable("movieId") Long movieId,
                                                        @PageableDefault(page =0, size = 3, sort ="boardId",direction = Sort.Direction.DESC)Pageable pageable){
        Map<String, Object> result = boardService.listBoard(movieId, pageable);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    // 게시글 상세보기 (0순위)
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> boardDetail(@PathVariable("boardId") Long boardId){
        Map<String, Object> result = boardService.detailBoard(boardId);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    // 게시글 생성 (0순위)
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> boardCreate(@RequestBody BoardCreateDto boardCreateDto){
        Map<String, Object> result = new HashMap<>();
        try {
            Long boardId = boardService.createBoard(boardCreateDto);
            result.put("boardId", boardId);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            result.put("message", "게시글이 잘못됐거나  작성자가 잘못됐거나 ");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);

        }

    }

    // 게시글 수정 (0순위)
    @PostMapping("/modify")
    public ResponseEntity<Map<String, Object>> boardUpdate(@RequestBody BoardUpdateDto boardUpdateDto){
        Map<String, Object> result = new HashMap<>();
        Long boardId = boardService.updateBoard(boardUpdateDto);
        result.put("boardId", boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 글 상태바꾸기 ( 삭제, 신고, 평범)
    // 1:normal 2: banned 3: deleted
    @PostMapping("/status")
    public void boardChangeStatus(@RequestParam(name="boardId") Long boardId, @RequestParam(name="statusCode") int statusCode){

        boardService.statusBoard(boardId, statusCode);
    }


    // 게시글 인기목록 (1순위)
    // 게시글 좋아요 (1순위)
    // 게시글 북마크 (1순위)



}
