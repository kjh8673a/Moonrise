package moonrise.pjt1.board.controller;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BoardForm {

    private String title;
    private String content;
    private LocalDateTime dateTime;
    private Long memberId;
}
