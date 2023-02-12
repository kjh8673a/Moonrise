package moonrise.pjt1.rating.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rating", indexes = @Index(name = "movie_id", columnList = "movie_id"))
public class RatingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private long id;
    //평가항목5개
    @Column(nullable = false)
    private long direction; //연출
    @Column(nullable = false)
    private long sound;  //사운드
    @Column(nullable = false)
    private long story;  //스토리
    @Column(nullable = false)
    private long acting; //연기
    @Column(nullable = false)
    private long visual; //영상미
    //총점
    private long total;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie; //index
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //fk


    @Builder
    public RatingEntity(long direction, long sound, long story, long acting, long visual, long total,
                        Member member, Movie movie) {
        this.direction = direction;
        this.sound = sound;
        this.story = story;
        this.acting = acting;
        this.visual = visual;
        this.total = total;
        this.member = member;
        this.movie = movie;
    }
}

