package moonrise.pjt1.board.controller;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BoardForm {
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;
}
