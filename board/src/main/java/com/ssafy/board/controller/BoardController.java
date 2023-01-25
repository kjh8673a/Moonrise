package com.ssafy.board.controller;


import com.ssafy.board.dto.Board;
import com.ssafy.board.jpa.BoardEntity;
import com.ssafy.board.service.Boardservice;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
public class BoardController {
    Boardservice boardservice;
    @Autowired
    public BoardController(Boardservice boardservice){
        this.boardservice=boardservice;
    }
    @GetMapping("/check")
    public String check(){
        return "hi";
    }

    @GetMapping("/")
    public ResponseEntity getBoardAll(){
        Iterable<BoardEntity> boardlist=boardservice.getBoardAll();
        return ResponseEntity.status(HttpStatus.OK).body(boardlist);
    }

    @PostMapping("/")
    public ResponseEntity create(int id, String title, String description){
        Board board=Board.builder()
                .id(id)
                .title(title)
                .description(description)
                .build();
        boardservice.save(board);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable int id, String title, String description){
        Board board=Board.builder()
                .title(title)
                .description(description)
                .build();
        BoardEntity boardEntity= boardservice.update(id, board);
        return ResponseEntity.status(HttpStatus.OK).body(boardEntity);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deleteBoard(@PathVariable int id){
        boardservice.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제");
    }
}
