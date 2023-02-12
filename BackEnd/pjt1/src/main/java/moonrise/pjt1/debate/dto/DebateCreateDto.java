package moonrise.pjt1.debate.dto;

import lombok.Builder;
import lombok.Data;
@Data
public class DebateCreateDto {
    private String title;
    private String description;
    private int maxppl;
    private Long movieId;
    private String imagePath;

    @Builder
    public DebateCreateDto(String title, String description, int maxppl, Long movieId, String imagePath) {
        this.title = title;
        this.description = description;
        this.maxppl = maxppl;
        this.movieId = movieId;
        this.imagePath = imagePath;
    }
}

