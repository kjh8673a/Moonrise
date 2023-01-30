package moonrise.pjt1.party.dto;

import lombok.Data;

@Data
public class PartyListResponseDto {
    private Long partyId;
    private String title;
    private int partyPeople;
    private String location;

    public PartyListResponseDto(Long partyId, String title, int partyPeople, String location) {
        this.partyId = partyId;
        this.title = title;
        this.partyPeople = partyPeople;
        this.location = location;
    }
}
