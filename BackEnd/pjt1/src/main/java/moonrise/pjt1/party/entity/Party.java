package moonrise.pjt1.party.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Party {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;

    private String title;
    private String content;
    private LocalDateTime partyDate;
    private PartyStatus partyStatus;
    private int partyPeople;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "party")
    private List<PartyJoin> partyJoins = new ArrayList<>();
}
