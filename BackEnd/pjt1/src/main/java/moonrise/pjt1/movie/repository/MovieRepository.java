package moonrise.pjt1.movie.repository;

import moonrise.pjt1.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
