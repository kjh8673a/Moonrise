package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// board 엔티티와 id 데이터 형식
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
