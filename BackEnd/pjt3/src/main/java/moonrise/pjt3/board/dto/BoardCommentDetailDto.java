package moonrise.pjt3.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

//댓글 내용, 댓글 작성자이름, 댓글 작성자id , 글ID, gid, 댓글id, 댓글작성날짜
@Data
public class BoardCommentDetailDto {
    private Long commentId;
    private Long memberId;
    private String content;
    private String writer;
    private LocalDateTime dateTime;

}
