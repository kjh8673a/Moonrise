package moonrise.pjt1.rating.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ratingId;
    private int direction;
    private int sound;
    private int story;
    private int acting;
    private int visual;
    private int total;
    private long memberId;
    private long movieId;

    @Builder

    public RatingEntity(long ratingId, int direction, int sound, int story, int acting,
                        int visual, int total, long memberId, long movieId) {
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

