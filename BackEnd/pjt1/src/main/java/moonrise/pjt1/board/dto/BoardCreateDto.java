package moonrise.pjt1.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardCreateDto {
    private String content;
    private String title;
    private Long movieId;

}
