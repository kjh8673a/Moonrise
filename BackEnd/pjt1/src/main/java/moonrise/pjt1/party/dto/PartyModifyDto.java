package moonrise.pjt1.party.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartyModifyDto {
    private Long partyId;
    private String title;
    private String content;
    private int partyPeople;
    private String location;
    private boolean meetOnline;
    private LocalDateTime partyDate;
    private LocalDateTime deadLine;
    public PartyModifyDto(Long partyId, String title, String content,
                          int partyPeople, String location, boolean meetOnline,
                          LocalDateTime partyDate, LocalDateTime deadLine) {
        this.partyId = partyId;
        this.title = title;
        this.content = content;
        this.partyPeople = partyPeople;
        this.location = location;
        this.meetOnline = meetOnline;
        this.partyDate = partyDate;
        this.deadLine = deadLine;
    }
}
