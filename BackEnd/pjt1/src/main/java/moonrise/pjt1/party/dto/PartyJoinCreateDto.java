package moonrise.pjt1.party.dto;

import lombok.Data;
import moonrise.pjt1.party.entity.PartyJoinStatus;

import java.time.LocalDateTime;

@Data
public class PartyJoinCreateDto {
    private Long joinId;

    private String message;
    private Long partyId;
    private Long memberId;

    public PartyJoinCreateDto(Long joinId, String message, Long partyId, Long memberId) {
        this.joinId = joinId;
        this.message = message;
        this.partyId = partyId;
        this.memberId = memberId;
    }
}
