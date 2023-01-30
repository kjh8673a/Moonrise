package moonrise.pjt1.party.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartyCreateDto {
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private int partyPeople;
    private String location;
    private boolean meetOnline;

    public PartyCreateDto(Long memberId, Long movieId, String title, String content, LocalDateTime localDateTime, int partyPeople, String location, boolean meetOnline) {
        this.memberId = memberId;
        this.movieId = movieId;
        this.title = title;
        this.content = content;
        this.localDateTime = localDateTime;
        this.partyPeople = partyPeople;
        this.location = location;
        this.meetOnline = meetOnline;
    }
}
