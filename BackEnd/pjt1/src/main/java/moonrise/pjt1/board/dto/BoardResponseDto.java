package moonrise.pjt1.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter @Setter
public class BoardResponseDto {
    private Long id;
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;

}
