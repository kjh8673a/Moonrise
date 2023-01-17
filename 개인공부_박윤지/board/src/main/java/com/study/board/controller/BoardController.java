package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import com.study.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Controller
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public BoardController(BoardService boardService,
                           BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/board/write")
   public String boardWriteForm(){
        return "boardwrite";
   }

   @PostMapping("/board/writepro")
   public String boardWritePro(Board board, Model model, MultipartFile file)throws Exception{
        boardService.write(board, file);

        // 메시지 alert
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
   }
// Model model 데이터를 담아서 우리가 보는 페이지로 넘겨줄때 사용
   @GetMapping("/board/list")
    public String boardlist(Model model){
        model.addAttribute("list", boardService.boardList());
        return "boardlist";
   }

   @GetMapping("/board/view") //localhost:8090/board/view?id=1
    public String boardview(Model model, Integer id){

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
   }

   @GetMapping("/board/modify/{id}")
   public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
   }

   @PostMapping("/board/update/{id}")
   public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file)throws Exception {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp, file);
        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
   }

   @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
   }
}
