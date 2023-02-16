package moonrise.pjt3.board.dto;

import lombok.Data;

@Data
public class BoardUpdateDto {
    private Long boardId;
    private Long memberId;
    private String content;
    private String title;
    private Long movieId;
}
