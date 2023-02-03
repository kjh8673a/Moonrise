package moonrise.pjt3.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import moonrise.pjt3.member.entity.Member;
import moonrise.pjt3.party.dto.PartyCommentCreateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_comment_id")
    private Long id;

    private String content;
    private LocalDateTime commentWriteTime;
    private boolean showPublic;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String writer;

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
        partyComment.setWriter(member.getProfile().getNickname());
        return partyComment;
    }
}
