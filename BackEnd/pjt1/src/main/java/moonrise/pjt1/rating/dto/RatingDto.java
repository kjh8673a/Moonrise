package moonrise.pjt1.rating.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingDto {
    private long ratingId; //pk
    //평가항목5개
    private long story;  //스토리
    private long acting; //연기
    private long direction; //연출
    private long visual; //영상미
    private long sound;  //사운드
    //총점
    private long total;
    private long memberId; //fk
    private long movieId; //index

    @Builder
    public RatingDto(long ratingId, long direction, long sound, long story, long acting, long visual, long total, long memberId, long movieId) {
        this.ratingId = ratingId;
        this.direction = direction;
        this.sound = sound;
        this.story = story;
        this.acting = acting;
        this.visual = visual;
        this.total = total;
        this.memberId = memberId;
        this.movieId = movieId;
    }
}
