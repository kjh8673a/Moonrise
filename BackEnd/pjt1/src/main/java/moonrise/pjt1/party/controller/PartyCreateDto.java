package moonrise.pjt1.party.controller;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartyCreateDto {
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private int partyPeople;
    private String location;

}
