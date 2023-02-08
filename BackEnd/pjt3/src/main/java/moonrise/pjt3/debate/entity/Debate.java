package moonrise.pjt3.debate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt3.debate.dto.DebateCreateDto;
import moonrise.pjt3.member.entity.Member;
import moonrise.pjt3.movie.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Debate {

    @Id @Column(name = "debate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private int maxppl;

    @Enumerated(EnumType.STRING)
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
    public Debate(DebateCreateDto debateCreateDto, Movie movie,
                  Member member, DebateInfo debateInfo) {
        this.title = debateCreateDto.getTitle();
        this.description = debateCreateDto.getDescription();
        this.createDate = LocalDateTime.now();
        this.maxppl = debateCreateDto.getMaxppl();
        this.debateStatus = DebateStatus.생성;
        this.movie = movie;
        this.member = member;
        this.debateInfo = debateInfo;
    }
}
