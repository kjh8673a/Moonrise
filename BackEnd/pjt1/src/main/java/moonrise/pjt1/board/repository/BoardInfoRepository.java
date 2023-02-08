package moonrise.pjt1.board.repository;



import moonrise.pjt1.board.entity.BoardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardInfoRepository extends JpaRepository<BoardInfo, Long> {
    @Query(value = "select bi.viewCnt from BoardInfo bi where bi.id = :boardId")
    Integer findBoardViewCnt(@Param("boardId") Long boardId);
}
