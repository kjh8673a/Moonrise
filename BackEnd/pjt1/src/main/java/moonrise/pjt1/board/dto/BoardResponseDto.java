package moonrise.pjt1.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.member.entity.Member;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter @Setter
public class BoardResponseDto {
    // 게시판 목록 및 상세보기

    private Long id;
    private Long memberId;
    private Long movieId;
    private String title;
    private String content;
    private LocalDateTime dateTime;

    //  작성자 프로필 이미지
//    private String userImg;
//    private int like;
//    private int mark;
    // 사용자 이름? 모르겠음
//    private String nickname;

}
