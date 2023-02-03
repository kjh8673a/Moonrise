package moonrise.pjt3.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter @Setter
public class BoardListResponseDto {
    // 게시판 목록

    private Long id;
//    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime dateTime;

    //  작성자 프로필 이미지
//    private String userImg;
//    private int like;
//    private int mark;
    // 사용자 이름? 모르겠음
    private String writer;

    public BoardListResponseDto(Long id, String title, String content, LocalDateTime dateTime, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.writer = writer;
    }
}
