package moonrise.pjt1.board.dto;

import java.time.LocalDateTime;

public interface CommentForDetailProjectionDto {
	Long getCommentId();
	Long getMemberId();
	String getContent();
	String getWriter();
	LocalDateTime getDateTime();
}
