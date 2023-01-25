package com.ssafy.board.service;

import com.ssafy.board.dto.Board;
import com.ssafy.board.jpa.BoardEntity;
import com.ssafy.board.jpa.BoardRepository;
import com.ssafy.board.jpa.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements Boardservice {

    private BoardRepository boardRepository;
    private CustomRepository customRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardEntity save(Board board) {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .build();
        boardRepository.save(boardEntity);
        return boardEntity;
    }

    @Override
    public Iterable<BoardEntity> getBoardAll() {
        return boardRepository.findAll();
    }

    @Override
    public BoardEntity update(int id, Board board) {
        delete(id);
        BoardEntity b = save(board);
        return b;
    }

    @Override
    public void delete(int id) {
        boardRepository.deleteById(id);
    }
}
