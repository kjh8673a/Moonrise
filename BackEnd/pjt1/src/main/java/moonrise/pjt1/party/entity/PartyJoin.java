package moonrise.pjt1.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.party.dto.PartyJoinCreateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class PartyJoin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_join_id")
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private PartyJoinStatus partyJoinStatus;
    private LocalDateTime joinDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String applier;
    public void setParty(Party party){
        this.party = party;
        party.getPartyJoins().add(this);
    }

    public static PartyJoin createPartyJoin(PartyJoinCreateDto partyJoinCreateDto, Member member, Party party){
        PartyJoin partyJoin = new PartyJoin();
        partyJoin.setMessage(partyJoinCreateDto.getMessage());
        partyJoin.setPartyJoinStatus(PartyJoinStatus.승인대기);
        partyJoin.setJoinDate(LocalDateTime.now());
        partyJoin.setMember(member);
        partyJoin.setParty(party);
        partyJoin.setApplier(member.getProfile().getNickname());
        return partyJoin;
    }
}
