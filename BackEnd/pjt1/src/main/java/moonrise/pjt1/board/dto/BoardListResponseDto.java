package moonrise.pjt1.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter @Setter
public class BoardListResponseDto {
    // 게시판 목록

    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateTime;

    //  작성자 프로필 이미지
//    private String userImg;
    private int likeCnt;
    private int commentCnt;
    private int viewCnt;
//    private int mark;
    // 사용자 이름? 모르겠음
    private String writer;

    public BoardListResponseDto(Long id, String title, String content, LocalDateTime dateTime, int likeCnt, int commentCnt, int viewCnt, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.viewCnt = viewCnt;
        this.writer = writer;
    }
}
