package moonrise.pjt1.board.dto;

import lombok.Data;

@Data
public class BoardLikeDto {
    private Long boardId;

    // 1: 좋아요 0: 좋아요 취소
    private int status;
}
