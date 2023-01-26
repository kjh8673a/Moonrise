package moonrise.pjt1.party.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.party.controller.PartyCreateDto;

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
    private int partyPeople;
    private String location;
    private PartyStatus partyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //호스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "party")
    private List<PartyJoin> partyJoins = new ArrayList<>();

    @OneToMany(mappedBy = "party")
    private List<PartyComment> partyComments = new ArrayList<>();

    public static Party createParty(PartyCreateDto partyCreateDto, Member member, Movie movie){
        Party party = new Party();
        party.setTitle(partyCreateDto.getTitle());
        party.setContent(partyCreateDto.getContent());
        party.setPartyDate(partyCreateDto.getLocalDateTime());
        party.setPartyPeople(partyCreateDto.getPartyPeople());
        party.setLocation(partyCreateDto.getLocation());
        party.setPartyStatus(PartyStatus.모집중);

        party.setMember(member);
        party.setMovie(movie);
        return party;
    }
}
