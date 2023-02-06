package moonrise.pjt1.party.dto;

import lombok.Data;
import moonrise.pjt1.party.entity.PartyJoinStatus;

import java.time.LocalDateTime;

@Data
public class PartyJoinCreateDto {

    private String message;
    private Long partyId;

    public PartyJoinCreateDto(String message, Long partyId) {
        this.message = message;
        this.partyId = partyId;
    }
}
