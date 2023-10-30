package moonrise.pjt1.board.request;

import lombok.Data;

@Data
public class BoardBookmarkReq {
	private Long userId;
	private Long boardId;
}
