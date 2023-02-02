package moonrise.pjt1.party.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartyListResponseDto {
    private Long partyId;
    private String title;
    private int partyPeople;
    private String location;
    private LocalDateTime partyDate;

    public PartyListResponseDto(Long partyId, String title, int partyPeople, String location,LocalDateTime partyDate) {
        this.partyId = partyId;
        this.title = title;
        this.partyPeople = partyPeople;
        this.location = location;
        this.partyDate = partyDate;
    }
}
