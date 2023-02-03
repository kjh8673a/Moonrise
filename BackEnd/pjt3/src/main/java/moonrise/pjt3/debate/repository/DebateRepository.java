package moonrise.pjt3.debate.repository;

import moonrise.pjt3.debate.entity.Debate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebateRepository extends JpaRepository<Debate,Long> {

    @Query(value = "select d from Debate d join fetch d.debateInfo where d.movie.id = :movieId")
    List<Debate> findDebateList(@Param("movieId") Long movieId);

}
