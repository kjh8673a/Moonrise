package com.ssafy.board.service;

import com.ssafy.board.dto.Board;
import com.ssafy.board.jpa.BoardEntity;

public interface Boardservice {
    BoardEntity save(Board board);
    Iterable<BoardEntity> getBoardAll();
    BoardEntity update(int id, Board board);
    void delete(int id);
}
