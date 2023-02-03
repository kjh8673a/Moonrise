package moonrise.pjt3.party.dto;

import lombok.Data;

@Data
public class PartyStatusRequestDto {
    private Long partyId;
    private int changeState;
}
