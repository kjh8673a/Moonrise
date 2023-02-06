package moonrise.pjt1.party.dto;

import lombok.Data;

@Data
public class PartyCommentCreateDto {
    private Long partyId;
    private String content;
    private boolean showPublic;

    private Long groupId;    // 원댓글일시 groupId = 0, 대댓글일시 원댓글의 pk

    private int isNestedComment;      // 원댓글일시 0 , 대댓글일시 1


    public PartyCommentCreateDto(Long partyId, String content, boolean showPublic, Long groupId, int isNestedComment) {
        this.partyId = partyId;
        this.content = content;
        this.showPublic = showPublic;
        this.groupId = groupId;
        this.isNestedComment = isNestedComment;
    }
}
