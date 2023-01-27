package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
