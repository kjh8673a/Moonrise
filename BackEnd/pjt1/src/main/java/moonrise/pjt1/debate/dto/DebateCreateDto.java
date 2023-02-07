package moonrise.pjt1.debate.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DebateCreateDto {
    private String title;
    private String description;
    private String img;
    private int maxppl;
    private Long movieId;
    @Builder
    public DebateCreateDto(String title, String description, String img, int maxppl, Long movieId) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.maxppl = maxppl;
        this.movieId = movieId;
    }
}
