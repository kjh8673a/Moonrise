package moonrise.pjt1.debate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "debate")
public class Debate {

    @Id @Column(name = "debate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true) //사진은 선택사항으로
    private String img;

    @Column(nullable = true)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private int maxppl;

    @Column(nullable = true)
    private int nowppl;

    private DebateStatus debateStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "debate_info_id")
    private DebateInfo debateInfo;

    @Builder
    public Debate(long id, String title, String description, String img, LocalDateTime createDate,
                  int maxppl, int nowppl, DebateStatus debateStatus, Movie movie,
                  Member member, DebateInfo debateInfo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        this.createDate = createDate;
        this.maxppl = maxppl;
        this.nowppl = nowppl;
        this.debateStatus = debateStatus;
        this.movie = movie;
        this.member = member;
        this.debateInfo = debateInfo;
    }
}
