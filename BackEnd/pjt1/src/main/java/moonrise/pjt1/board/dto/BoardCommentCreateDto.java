package moonrise.pjt1.board.dto;

import lombok.Data;

@Data
public class BoardCommentCreateDto {
    private Long boardId;
    private String content;
    // 원댓글일시 groupId = 0, 대댓글일시 원댓글의 pk
    private Long groupId;

    // 원댓글일시 0 , 대댓글일시 1
    private int isNestedComment;

}
