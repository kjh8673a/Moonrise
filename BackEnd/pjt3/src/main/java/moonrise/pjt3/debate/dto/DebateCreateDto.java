package moonrise.pjt3.debate.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DebateCreateDto {
    private String title;
    private String description;
    private int maxppl;
    private Long movieId;
    @Builder
    public DebateCreateDto(String title, String description, int maxppl, Long movieId) {
        this.title = title;
        this.description = description;
        this.maxppl = maxppl;
        this.movieId = movieId;
    }
}
