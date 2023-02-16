package moonrise.pjt3.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import moonrise.pjt3.member.entity.Member;
import moonrise.pjt3.movie.entity.Movie;
import moonrise.pjt3.party.dto.PartyCreateDto;
import moonrise.pjt3.party.dto.PartyModifyDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Party {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;

    private String title;
    private String content;
    private LocalDateTime partyDate;
    private LocalDateTime deadLine;
    private int partyPeople;
    private String location;
    private boolean meetOnline;

    @Enumerated(EnumType.STRING)
    private PartyStatus partyStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //호스트

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @JsonIgnore
    @OneToMany(mappedBy = "party")
    private List<PartyJoin> partyJoins = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "party")
    private List<PartyComment> partyComments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "party_info_id")
    private PartyInfo partyInfo;
    public void setMovie(Movie movie){
        this.movie = movie;
        movie.getParties().add(this);
    }
    public static Party createParty(PartyCreateDto partyCreateDto, Member member, Movie movie,PartyInfo partyInfo){
        Party party = new Party();
        party.setTitle(partyCreateDto.getTitle());
        party.setContent(partyCreateDto.getContent());
        party.setMeetOnline(partyCreateDto.isMeetOnline());
        party.setPartyDate(partyCreateDto.getPartyDate());
        party.setDeadLine(partyCreateDto.getDeadLine());
        party.setPartyPeople(partyCreateDto.getPartyPeople());
        party.setLocation(partyCreateDto.getLocation());
        party.setPartyStatus(PartyStatus.모집중);
        party.setMember(member);
        party.setMovie(movie);
        party.setPartyInfo(partyInfo);
        return party;
    }
    public void modifyParty(PartyModifyDto partyModifyDto){
        this.title = partyModifyDto.getTitle();
        this.content = partyModifyDto.getContent();
        this.partyPeople = partyModifyDto.getPartyPeople();
        this.location = partyModifyDto.getLocation();
        this.meetOnline = partyModifyDto.isMeetOnline();
        this.partyDate = partyModifyDto.getPartyDate();
        this.deadLine = partyModifyDto.getDeadLine();
    }
}
