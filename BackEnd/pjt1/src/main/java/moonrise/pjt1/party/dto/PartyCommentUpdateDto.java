package moonrise.pjt1.party.dto;

import lombok.Data;

@Data
public class PartyCommentUpdateDto {
    private Long commentId;
    private Long memberId;
    private String content;
    private boolean showPublic;
}
