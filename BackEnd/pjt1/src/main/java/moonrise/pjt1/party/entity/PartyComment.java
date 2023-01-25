package moonrise.pjt1.party.entity;

import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;

@Entity
public class PartyComment {
    @Id @GeneratedValue
    @Column(name = "party_comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
