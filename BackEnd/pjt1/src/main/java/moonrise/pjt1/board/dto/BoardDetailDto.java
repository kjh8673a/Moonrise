package moonrise.pjt1.board.dto;

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
    private List<BoardComment> boardComments = new ArrayList<>();
    private int viewCnt;
    private int commentCnt;
    private int likeCnt;
    private Boolean isLike;
    private Boolean isBookmark;

    public BoardDetailDto(Long movieId, String title, String content, LocalDateTime dateTime, String writer, List<BoardComment> boardComments, int viewCnt, int commentCnt, int likeCnt, Boolean isLike, Boolean isBookmark) {
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
