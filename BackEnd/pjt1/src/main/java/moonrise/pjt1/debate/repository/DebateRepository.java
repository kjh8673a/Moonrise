package moonrise.pjt1.debate.repository;

import moonrise.pjt1.debate.entity.Debate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebateRepository extends JpaRepository<Debate, Long> {
    @Query(value = "select " +
            "new moonrise.pjt1.debate.dto.DebateListResponseDto(" +
            "d.id, d.title, d.member.profile.nickname, d.imagePath, d.maxppl,d.debateInfo.nowppl, d.debateStatus) " +
            "from Debate d  where d.movie.id = :movieId")
    Page<Debate> findDebateList(@Param("movieId") Long movieId, Pageable pageable);

//    @Query(value = "select d from Debate d join fetch d.debateInfo where d.movie.id = :movieId")
//    List<Debate> findDebateList(@Param("movieId") Long movieId);
}
