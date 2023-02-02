package moonrise.pjt1.rating.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingDto {
    private long ratingId;
    private int direction;
    private int sound;
    private int story;
    private int acting;
    private int visual;
    private long memberId;
    private long movieId;

    @Builder
    public RatingDto(long ratingId, int direction, int sound, int story, int acting, int visual, long memberId, long movieId) {
        this.ratingId = ratingId;
        this.direction = direction;
        this.sound = sound;
        this.story = story;
        this.acting = acting;
        this.visual = visual;
        this.memberId = memberId;
        this.movieId = movieId;
    }
}
