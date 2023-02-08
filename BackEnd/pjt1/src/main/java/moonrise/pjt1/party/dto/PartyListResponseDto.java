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
    private int likeCnt;
    private int viewCnt;
    private int commentCnt;
    private String writer;
    private LocalDateTime deadline;

    public PartyListResponseDto(Long partyId, String title, int partyPeople, String location, LocalDateTime partyDate, int likeCnt, int viewCnt, int commentCnt, String writer, LocalDateTime deadline) {
        this.partyId = partyId;
        this.title = title;
        this.partyPeople = partyPeople;
        this.location = location;
        this.partyDate = partyDate;
        this.likeCnt = likeCnt;
        this.viewCnt = viewCnt;
        this.commentCnt = commentCnt;
        this.writer = writer;
        this.deadline = deadline;
    }
}
