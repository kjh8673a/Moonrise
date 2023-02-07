package moonrise.pjt1.board.dto;

import lombok.Data;

@Data
public class BoardUpdateDto {
    private Long boardId;
    private String content;
    private String title;

}
