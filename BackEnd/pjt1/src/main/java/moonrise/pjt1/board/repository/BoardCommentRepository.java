package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Long> {
}
