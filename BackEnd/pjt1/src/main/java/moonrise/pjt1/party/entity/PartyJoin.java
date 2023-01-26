package moonrise.pjt1.party.entity;

import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;

@Entity
public class PartyJoin {
    @Id @GeneratedValue
    @Column(name = "party_join_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
