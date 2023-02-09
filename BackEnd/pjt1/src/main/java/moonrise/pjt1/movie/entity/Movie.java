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
@Table(name = "movie", indexes = {@Index(name = "idx_ko", columnList = "ko"),
        @Index(name = "idx_en", columnList = "en")})
public class Movie {
    @Id
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "original_title")
    private String originalTitle;
    private String title;
    private String img;
    private String director;
    private double popularity;
    private Character ko;
    private Character en;

    @Column(name = "release_date")
    private String releaseDate;
    @OneToMany
    private List<Genre> genres;
    @OneToOne(mappedBy = "movie")
    private RatingEntity rating;

    //
    @OneToMany(mappedBy = "movie")
    private List<Board> boards;
    @OneToMany(mappedBy = "movie")
    private List<Party> parties;

    @Builder

    public Movie(Long id, String originalTitle, String title, String img, String director,
                 double popularity, Character ko, Character en, String releaseDate, List<Genre> genres) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.img = img;
        this.director = director;
        this.popularity = popularity;
        this.ko = ko;
        this.en = en;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }
}

