package moonrise.pjt1.debate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt1.debate.entity.DebateStatus;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class DebateDto {
    private long id;
    private String title;
    private String description;
    private String imgPath;
    private LocalDateTime createDate;
    private int maxppl;
    private int nowppl;
    private DebateStatus debateStatus;
    private long movieId;

    @Builder
    public DebateDto(long id, String title, String description, String imgPath, LocalDateTime createDate,
                     int maxppl, int nowppl, DebateStatus debateStatus, long movieId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.createDate = createDate;
        this.maxppl = maxppl;
        this.nowppl = nowppl;
        this.debateStatus = debateStatus;
        this.movieId = movieId;
    }
}
