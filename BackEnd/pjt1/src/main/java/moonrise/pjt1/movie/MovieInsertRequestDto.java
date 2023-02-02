package moonrise.pjt1.movie;

import lombok.*;
import moonrise.pjt1.movie.entity.Movie;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInsertRequestDto {
    private Long id;
    private String originalTitle;
    private String title;
    private double popularity;
    private String release_date;
    private List<String> genre;

    public Movie movieBuilder(){
        Movie movie = new Movie();
        movie.setId(this.id);
        movie.setTitle(this.title);
        movie.setOriginalTitle(this.originalTitle);
        movie.setReleaseDate(this.release_date);
        movie.setPopularity(this.popularity);

        return movie;
    }
}
