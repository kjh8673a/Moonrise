package moonrise.pjt1.board.dto;

import lombok.Data;

@Data
public class BoardCommentCreateDto {
    private Long boardId;
    private Long memberId;
    private String content;
//    private Long groupId;

}
