package moonrise.pjt1.party.dto;

import lombok.Data;

@Data
public class PartyStatusRequestDto {
    private Long partyId;
    private int changeState;
}
