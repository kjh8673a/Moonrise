package moonrise.pjt1.rating.repository;

import moonrise.pjt1.rating.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    long countByMovieIdEquals(Long movieId);

    @Query(value = "select r from RatingEntity r where r.movie.id = :movieId")
    List<RatingEntity> findRatingList(@Param("movieId") long movieId);

    @Query(value = "select r from RatingEntity r where r.movie.id = :movieId and r.member.id= :memberId")
    RatingEntity findPersonal(@Param("movieId") long movieId, @Param("memberId") long memberId);

    @Query(value = "select r from RatingEntity r where r.movie.id = :movieId and r.member.id= :memberId")
    boolean booleanPersonal(@Param("movieId") long movieId, @Param("memberId") long memberId);
}
