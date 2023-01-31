package moonrise.pjt1.movie;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieInsertRequestDto {
    private Long id;
    private String originalTitle;
    private String title;
    private double popularity;
    private String release_date;
    private List<String> genre;


}
