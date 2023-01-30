package moonrise.pjt1.board.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BoardDetailDto {
    // 게시판 생성
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private String writer;

    public BoardDetailDto(Long memberId, Long movieId, String title, String content, LocalDateTime dateTime, String writer) {
        this.memberId = memberId;
        this.movieId = movieId;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.writer = writer;
    }
}
