package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    // 글 작성 처리 + 파일 첨부
    public void write(Board board, MultipartFile file) throws Exception{
        // 파일 처리
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID(); // 식별자 랜덤 생성
        String fileName = uuid + "_" + file.getOriginalFilename(); // 저장될 파일 이름
        File saveFile = new File(projectPath, "filename");
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }
    // 게시글 리스트 처리
    public List<Board> boardList(){
        return boardRepository.findAll();
    }
    // 특정 게시글 불러오기
    public Board boardView(Integer id){
        // findbyid(id) 만 하면 optional 값 가져옴
        return boardRepository.findById(id).get();
    }
    // 특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
