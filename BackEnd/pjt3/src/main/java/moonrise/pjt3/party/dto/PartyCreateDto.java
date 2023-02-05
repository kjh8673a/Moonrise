package moonrise.pjt3.party.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartyCreateDto {
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private int partyPeople;
    private String location;
    private boolean meetOnline;
    private LocalDateTime partyDate;
    private LocalDateTime deadLine;
    public PartyCreateDto(Long memberId, Long movieId, String title, String content,
                          int partyPeople, String location, boolean meetOnline,
                          LocalDateTime partyDate, LocalDateTime deadLine) {
        this.memberId = memberId;
        this.movieId = movieId;
        this.title = title;
        this.content = content;
        this.partyPeople = partyPeople;
        this.location = location;
        this.meetOnline = meetOnline;
        this.partyDate = partyDate;
        this.deadLine = deadLine;
    }
}
