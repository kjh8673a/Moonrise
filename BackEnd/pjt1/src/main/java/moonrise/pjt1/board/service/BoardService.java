package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.controller.BoardDetailDto;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getBoardList(){
        return boardRepository.findAll();
    }

    public BoardDetailDto getBoardDetail(Long id){
        Board board = boardRepository.getById(id);
        BoardDetailDto boardDetailDto = new BoardDetailDto();
        boardDetailDto.setId(board.getId());
        boardDetailDto.setTitle();
        boardDetailDto.set
        return boardDetailDto;
    }
}
