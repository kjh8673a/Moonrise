package moonrise.pjt1.debate.dto;

import lombok.Builder;
import lombok.Data;
import moonrise.pjt1.debate.entity.DebateStatus;

import java.time.LocalDateTime;

@Data
public class DebateReadResponseDto {
    private Long debateId;
    private String title;
    private String description;
    private String writer;
    private String imagePath;
    private int maxppl;
    private int nowppl;
    private DebateStatus debateStatus;
    private LocalDateTime createDate;
    @Builder
    public DebateReadResponseDto(Long debateId, String title, String description,
                                 String writer, String imagePath, int maxppl, int nowppl,
                                 DebateStatus debateStatus,LocalDateTime createDate) {
        this.debateId = debateId;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.maxppl = maxppl;
        this.nowppl = nowppl;
        this.debateStatus = debateStatus;
        this.createDate = createDate;
        this.imagePath = imagePath;
    }
}
