package moonrise.pjt1.party.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt1.party.entity.PartyJoinStatus;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class PartyJoinListDto {
    private String title;
    private String location;
    private LocalDateTime partyDate;
    private LocalDateTime joinDate;
    private PartyJoinStatus partyJoinStatus;
    private String imagePath;
    private Long partyId;

    @Builder
    public PartyJoinListDto(String title, String location, LocalDateTime partyDate, LocalDateTime joinDate,
                            PartyJoinStatus partyJoinStatus,String imagePath, Long partyId) {
        this.title = title;
        this.location = location;
        this.partyDate = partyDate;
        this.joinDate = joinDate;
        this.partyJoinStatus = partyJoinStatus;
        this.imagePath = imagePath;
        this.partyId = partyId;
    }
}
