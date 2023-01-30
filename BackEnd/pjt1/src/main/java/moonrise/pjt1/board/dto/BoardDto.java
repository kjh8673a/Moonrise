package moonrise.pjt1.board.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BoardCreateDto {
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;
}
