package moonrise.pjt1.board.dto;

import lombok.Builder;
import lombok.Data;
import moonrise.pjt1.board.entity.BoardComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardDetailDto {

    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private String writer;
    private List<CommentForDetailProjectionDto> boardComments = new ArrayList<>();
    private Long viewCnt;
    private Long commentCnt;
    private Long likeCnt;
    private Boolean isLike;
    private Boolean isBookmark;

    @Builder
    public BoardDetailDto(Long movieId, String title, String content, LocalDateTime dateTime, String writer, List<CommentForDetailProjectionDto> boardComments, Long viewCnt, Long commentCnt, Long likeCnt, Boolean isLike, Boolean isBookmark) {
        this.movieId = movieId;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.writer = writer;
        this.boardComments = boardComments;
        this.viewCnt = viewCnt;
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
        this.isLike = isLike;
        this.isBookmark = isBookmark;
    }

}
