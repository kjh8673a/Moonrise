package moonrise.pjt1.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardForDetailProjectionDto {
	Long boardId;
	Long movieId;
	String title;
	String content;
	LocalDateTime dateTime;
	String writer;
	Long viewCnt;
	Long commentCnt;
	Long likeCnt;
}
