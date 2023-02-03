package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Long> {
    @Query(value = "select bc from BoardComment as bc order by bc.groupId")
    List<BoardComment> getCommentList();

}
