package moonrise.pjt1.board.dto;

import lombok.Data;

@Data
public class BoardCommentUpdateDto {
    private Long commentId;
    private String content;

}
