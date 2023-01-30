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
}
