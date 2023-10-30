package moonrise.pjt1.board.request;

import lombok.Data;

@Data
public class BoardLikeReq {
	private Long userId;
	private Long boardId;
}
