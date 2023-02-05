package moonrise.pjt3.board.dto;

import lombok.Data;
import moonrise.pjt3.board.entity.BoardComment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDetailDto {

    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private String writer;
    private List<BoardComment> comments;

    public BoardDetailDto(Long memberId, Long movieId, String title, String content, LocalDateTime dateTime, String writer, List<BoardComment> comments) {
        this.memberId = memberId;
        this.movieId = movieId;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.writer = writer;
        this.comments = comments;
    }
}
