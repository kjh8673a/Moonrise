package moonrise.pjt1.board.dto;

import lombok.Data;

@Data
public class BoardBookmarkDto {
    private Long boardId;

    // 1: 북마크 0: 북마크 취소
    private int status;
}
