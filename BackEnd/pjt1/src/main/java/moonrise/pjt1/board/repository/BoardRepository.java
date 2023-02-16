package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board,Long> {
//    @Query("select new moonrise.pjt1.board.dto.BoardListResponseDto" +"(b.id,b.title,b.content,b.dateTime, b.member.profile.nickname) from Board as b where b.movie.id =:movieId ")
    @Query("select b from Board b where b.movie.id =:movieId and b.boardInfo.boardStatus ='NORMAL'")
    Page<Board> findByMovieId(@Param("movieId") Long movieId, Pageable pageable);

    @Query("select b from Board b where b.member.id =:userId order by b.id desc")
    List<Board> findByUserId(@Param("userId") Long userId);
}
