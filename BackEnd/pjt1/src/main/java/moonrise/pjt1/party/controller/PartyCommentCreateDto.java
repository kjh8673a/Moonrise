package moonrise.pjt1.party.controller;

import lombok.Data;

@Data
public class PartyCommentCreateDto {
    private Long partyId;
    private Long memberId;
    private String content;
    private boolean showPublic;
}
