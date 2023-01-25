package com.ssafy.board.jpa;

import com.ssafy.board.dto.Board;
import org.springframework.data.repository.CrudRepository;


public interface BoardRepository extends CrudRepository<BoardEntity, Integer> {

}
