package moonrise.pjt1.movie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.party.entity.Party;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter @Setter @NoArgsConstructor
public class Movie {
    @Id
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "original_title")
    private String originalTitle;
    private double popularity;

    @Column(name = "release_date")
    private String releaseDate;
    private String title;

    @OneToMany(mappedBy = "movie")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Party> parties = new ArrayList<>();
}
