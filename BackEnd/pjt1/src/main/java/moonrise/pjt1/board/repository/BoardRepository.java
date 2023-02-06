package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface BoardRepository extends JpaRepository<Board,Long> {
    @Query("select new moonrise.pjt1.board.dto.BoardListResponseDto" +"(b.id,b.title,b.content,b.dateTime, b.member.profile.nickname) from Board as b where b.movie.id =:movieId ")
    Page<Board> findByMovieId(@Param("movieId") Long movieId, Pageable pageable);

}
