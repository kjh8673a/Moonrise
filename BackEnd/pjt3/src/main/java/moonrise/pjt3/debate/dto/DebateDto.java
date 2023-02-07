package moonrise.pjt3.debate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moonrise.pjt3.debate.entity.DebateStatus;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class DebateDto {
    private long id;
    private String title;
    private String description;
    private String img;
    private LocalDateTime createDate;
    private int maxppl;
    private int nowppl;
    private DebateStatus debateStatus;
    private long movieId;

    @Builder
    public DebateDto(long id, String title, String description, String img, LocalDateTime createDate,
                     int maxppl, int nowppl, DebateStatus debateStatus, long movieId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        this.createDate = createDate;
        this.maxppl = maxppl;
        this.nowppl = nowppl;
        this.debateStatus = debateStatus;
        this.movieId = movieId;
    }
}
