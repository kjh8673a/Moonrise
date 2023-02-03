package moonrise.pjt1.rating.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "totalrating")
public class TotalRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long totalratingId;
    private int direction;
    private int sound;
    private int story;
    private int acting;
    private int visual;
    private long movieId;

    @Builder

    public TotalRatingEntity(long totalratingId, int direction, int sound,
                             int story, int acting, int visual, long movieId) {
        this.totalratingId = totalratingId;
        this.direction = direction;
        this.sound = sound;
        this.story = story;
        this.acting = acting;
        this.visual = visual;
        this.movieId = movieId;
    }
