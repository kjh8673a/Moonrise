package moonrise.pjt3.party.dto;

import lombok.Data;

@Data
public class PartyJoinCreateDto {

    private String message;
    private Long partyId;
    private Long memberId;

    public PartyJoinCreateDto(String message, Long partyId, Long memberId) {
        this.message = message;
        this.partyId = partyId;
        this.memberId = memberId;
    }
}
