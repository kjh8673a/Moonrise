package moonrise.pjt1.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.party.entity.Party;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    private Long id;
    private String originalTitle;
    private String title;
    private double popularity;
    private String release_date;
    private List<String> genre;
    public void setResponseDto(Movie movie){
        this.id = movie.getId();
        this.originalTitle = movie.getOriginalTitle();
        this.title = movie.getTitle();
        this.popularity = movie.getPopularity();
        this.release_date = movie.getReleaseDate();
    }
}
