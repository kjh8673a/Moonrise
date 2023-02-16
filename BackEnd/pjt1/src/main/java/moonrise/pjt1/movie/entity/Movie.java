package moonrise.pjt1.movie.entity;

import lombok.*;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.party.entity.Party;
import moonrise.pjt1.rating.entity.RatingEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "original_title")
    private String originalTitle;
    private String title;
    private String img;
    private double popularity;

    @Column(name = "release_date")
    private String releaseDate;
    @OneToMany
    private List<Genre> genres;
    @ManyToOne
    private RatingEntity rating;

    @OneToMany(mappedBy = "movie")
    private List<Board> boards;
    @OneToMany(mappedBy = "movie")
    private List<Party> parties;

    @Builder
    public Movie(Long id, String originalTitle, String title, String img, double popularity, String releaseDate,
                 List<Genre> genres, RatingEntity rating, List<Board> boards, List<Party> parties) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.img = img;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.rating = rating;
        this.boards = boards;
        this.parties = parties;
    }
}

