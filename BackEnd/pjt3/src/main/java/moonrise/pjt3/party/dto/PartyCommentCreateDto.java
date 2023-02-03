package moonrise.pjt3.party.dto;

import lombok.Data;

@Data
public class PartyCommentCreateDto {
    private Long partyId;
    private Long memberId;
    private String content;
    private boolean showPublic;

    public PartyCommentCreateDto(Long partyId, Long memberId, String content, boolean showPublic) {
        this.partyId = partyId;
        this.memberId = memberId;
        this.content = content;
        this.showPublic = showPublic;
    }
}
