package moonrise.pjt1.debate.dto;

import lombok.Builder;
import lombok.Data;
import moonrise.pjt1.debate.entity.DebateStatus;

@Data
public class DebateListResponseDto {
    private Long debateId;
    private String title;
    private String writer;
    private String imagePath;
    private int maxppl;
    private int nowppl;
    private DebateStatus debateStatus;
    @Builder
    public DebateListResponseDto(Long debateId, String title, String writer, String imagePath, int maxppl, int nowppl,DebateStatus debateStatus) {
        this.debateId = debateId;
        this.title = title;
        this.writer = writer;
        this.maxppl = maxppl;
        this.nowppl = nowppl;
        this.debateStatus = debateStatus;
        this.imagePath = imagePath;
    }
}
