package moonrise.pjt3.movie.repository;

import moonrise.pjt3.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
