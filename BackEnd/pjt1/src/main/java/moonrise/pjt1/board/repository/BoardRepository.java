package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.dto.BoardListResponseDto;
import moonrise.pjt1.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board,Long> {
    @Query(value = "select new moonrise.pjt1.board.dto.BoardListResponseDto" +
            "(b.id,b.title,b.content,b.dateTime, b.member.profile.nickname) from Board b where b.movie.id = :movieId")
    List<BoardListResponseDto> findBoardList(@Param("movieId") Long movieId);
}
