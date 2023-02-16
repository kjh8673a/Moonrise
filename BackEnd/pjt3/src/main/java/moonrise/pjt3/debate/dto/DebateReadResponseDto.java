package moonrise.pjt3.debate.dto;

import lombok.Builder;
import lombok.Data;
import moonrise.pjt3.debate.entity.DebateStatus;

@Data
public class DebateReadResponseDto {
    private Long debateId;
    private String title;
    private String description;
    private String writer;
    private int maxppl;
    private int nowppl;
    private DebateStatus debateStatus;
    @Builder
    public DebateReadResponseDto(Long debateId, String title, String description, String writer, int maxppl, int nowppl, DebateStatus debateStatus) {
        this.debateId = debateId;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.maxppl = maxppl;
        this.nowppl = nowppl;
        this.debateStatus = debateStatus;
    }
}
