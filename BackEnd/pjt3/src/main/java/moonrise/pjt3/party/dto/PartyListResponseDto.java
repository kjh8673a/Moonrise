package moonrise.pjt3.party.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartyListResponseDto {
    private Long partyId;
    private String title;
    private int partyPeople;
    private String location;
    private LocalDateTime partyDate;
    private int viewCnt;

    public PartyListResponseDto(Long partyId, String title, int partyPeople,
                                String location,LocalDateTime partyDate,int viewCnt) {
        this.partyId = partyId;
        this.title = title;
        this.partyPeople = partyPeople;
        this.location = location;
        this.partyDate = partyDate;
        this.viewCnt = viewCnt;
    }
}
