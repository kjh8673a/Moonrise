package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board/write")
   public String boardWriteForm(){
        return "boardwrite";
   }
   @PostMapping("/board/writepro")
   public String boardWritePro(Board board){
        boardService.write(board);
       System.out.println("wwwrrrite ");
        return "";
   }
}
