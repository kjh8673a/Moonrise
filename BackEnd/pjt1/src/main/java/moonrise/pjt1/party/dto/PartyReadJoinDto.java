package moonrise.pjt1.party.dto;

import lombok.Builder;
import lombok.Data;
import moonrise.pjt1.party.entity.PartyJoinStatus;

import java.time.LocalDateTime;

@Data
public class PartyReadJoinDto {
    private Long id;
    private String message;
    private PartyJoinStatus partyJoinStatus;
    private LocalDateTime joinDate;
    private String applier;
    private String imagePath;
    @Builder
    public PartyReadJoinDto(Long id, String message, PartyJoinStatus partyJoinStatus, LocalDateTime joinDate, String applier, String imagePath) {
        this.id = id;
        this.message = message;
        this.partyJoinStatus = partyJoinStatus;
        this.joinDate = joinDate;
        this.applier = applier;
        this.imagePath = imagePath;
    }
}
