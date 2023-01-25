package moonrise.pjt1.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter @Setter @NoArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "write_date")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_info_id")
    private BoardInfo boardInfo;
}
