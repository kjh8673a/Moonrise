package moonrise.pjt1.board.dto;

import java.time.LocalDateTime;

public interface BoardForDetailProjectionDto {
	Long getBoardId();
	Long getMovieId();
	String getTitle();
	String getContent();
	LocalDateTime getDateTime();
	String getWriter();
	Long getViewCnt();
	Long getCommentCnt();
	Long getLikeCnt();
}
