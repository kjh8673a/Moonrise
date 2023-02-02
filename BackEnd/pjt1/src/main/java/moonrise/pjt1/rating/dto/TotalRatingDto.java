package moonrise.pjt1.rating.dto;

import lombok.Builder;

public class TotalRatingDto {
    private long totalRatingId;
    private int direction;
    private int sound;
    private int story;
    private int acting;
    private int visual;
    private long count;
    private long movieId;

    @Builder

    public TotalRatingDto(long totalRatingId, int direction, int sound, int story,
                          int acting, int visual, long count, long movieId) {
        this.totalRatingId = totalRatingId;
        this.direction = direction;
        this.sound = sound;
        this.story = story;
        this.acting = acting;
        this.visual = visual;
        this.count = count;
        this.movieId = movieId;
    }
}
