package moonrise.pjt1.board.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@Getter @Setter
public class MypageResponseDto {
    private Long boardId;
    private LocalDateTime dateTime;
    private String title;
    private String movieTitle;

    public MypageResponseDto(Long boardId, LocalDateTime dateTime, String title, String movieTitle) {
        this.boardId = boardId;
        this.dateTime = dateTime;
        this.title = title;
        this.movieTitle = movieTitle;
    }
}
