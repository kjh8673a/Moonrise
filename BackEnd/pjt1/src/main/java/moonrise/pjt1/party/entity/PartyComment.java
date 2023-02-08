package moonrise.pjt1.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.party.dto.PartyCommentCreateDto;
import static moonrise.pjt1.party.entity.PartyCommentStatus.*;
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
    private Long groupId = id;
    private int isNestedComment;

    @Enumerated(EnumType.STRING)
    private PartyCommentStatus partyCommentStatus = NORMAL;
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
        partyComment.setIsNestedComment(partyCommentCreateDto.getIsNestedComment());
        partyComment.setGroupId(partyCommentCreateDto.getGroupId());
        partyComment.setMember(member);
        partyComment.setWriter(member.getProfile().getNickname());
        return partyComment;
    }

    public void normalize() {
        this.setPartyCommentStatus(NORMAL);
    }

    public void banned() {
        this.setPartyCommentStatus(BANNED);
    }

    public void deleted() {
        this.setPartyCommentStatus(DELETED);
    }
}
