package moonrise.pjt1.debate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.party.entity.PartyInfo;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "debate")
public class DebateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long debate_id;

    @Column(nullable = false)
    private String debate_title;

    @Column(nullable = false)
    private String debate_description;

    @Column(nullable = true) //사진은 선택사항으로
    private String debate_img;

    @Column(nullable = true)
    private String debate_create;

    @Column(nullable = false)
    private int debate_maxppl;

    @Column(nullable = true)
    private int debate_nowppl;

    @Column(nullable = true)
    @ColumnDefault("false")//초기값 false
    private boolean debate_delete;

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
    private DebateInfoEntity debateInfoEntity;

    @Builder
    public DebateEntity(long debate_id, String debate_title, String debate_description, String debate_img,
                        String debate_create, int debate_maxppl, int debate_nowppl, boolean debate_delete, Movie movie,
                        Member member, DebateInfoEntity debateInfoEntity) {
        this.debate_id = debate_id;
        this.debate_title = debate_title;
        this.debate_description = debate_description;
        this.debate_img = debate_img;
        this.debate_create = debate_create;
        this.debate_maxppl = debate_maxppl;
        this.debate_nowppl = debate_nowppl;
        this.debate_delete = debate_delete;
        this.movie = movie;
        this.member = member;
        this.debateInfoEntity = debateInfoEntity;
    }
}
