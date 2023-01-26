package moonrise.pjt1.party.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void setParty(Party party){
        this.party = party;
        party.getPartyComments().add(this);
    }

    public static PartyComment createPartyComment(String content, Party party, Member member){
        PartyComment partyComment = new PartyComment();
        partyComment.setContent(content);
        partyComment.setParty(party);
        partyComment.setMember(member);
        return partyComment;
    }
}
