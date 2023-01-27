package moonrise.pjt1.party.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.party.controller.PartyCommentCreateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyComment {
    @Id @GeneratedValue
    @Column(name = "party_comment_id")
    private Long id;

    private String content;
    private LocalDateTime commentWriteTime;
    private boolean showPublic;
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

    public static PartyComment createPartyComment(PartyCommentCreateDto partyCommentCreateDto, Party party, Member member){
        PartyComment partyComment = new PartyComment();
        partyComment.setContent(partyCommentCreateDto.getContent());
        partyComment.setCommentWriteTime(LocalDateTime.now());
        partyComment.setShowPublic(partyCommentCreateDto.isShowPublic());
        partyComment.setParty(party);
        partyComment.setMember(member);
        return partyComment;
    }
}
