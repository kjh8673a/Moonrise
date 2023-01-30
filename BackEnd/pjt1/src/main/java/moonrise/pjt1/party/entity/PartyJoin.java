package moonrise.pjt1.party.entity;

import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PartyJoin {
    @Id @GeneratedValue
    @Column(name = "party_join_id")
    private Long id;

    private PartyJoinStatus partyJoinStatus;
    private LocalDateTime joinDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setParty(Party party){
        this.party = party;
        party.getPartyJoins().add(this);
    }

//    public static PartyJoin createPartyJoin(PartyCreateDto partyCreateDto, Member member, Movie movie){
//        Party party = new Party();
//        party.setTitle(partyCreateDto.getTitle());
//        party.setContent(partyCreateDto.getContent());
//        party.setPartyDate(partyCreateDto.getLocalDateTime());
//        party.setPartyPeople(partyCreateDto.getPartyPeople());
//        party.setLocation(partyCreateDto.getLocation());
//        party.setPartyStatus(PartyStatus.모집중);
//
//        party.setMember(member);
//        party.setMovie(movie);
//        return party;
//    }
}
