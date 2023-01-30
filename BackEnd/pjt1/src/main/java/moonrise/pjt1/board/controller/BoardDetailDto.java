package moonrise.pjt1.board.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter @Setter
public class BoardDetailDto {
    private Long id;
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;

}
