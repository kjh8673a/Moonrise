package moonrise.pjt1.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static moonrise.pjt1.board.entity.BoardStatus.NORMAL;

/**
 * 게시판 추가정보 - 좋아요 수, 댓글 수 등등..
 */
@Entity
@Getter
@Setter
public class BoardInfo {
    @Id @GeneratedValue
    @Column(name = "board_info_id")
    private Long id;

    @OneToOne(mappedBy = "boardInfo", fetch = FetchType.LAZY)
    private Board board;
    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus = NORMAL;
    private Long likeCnt;
    private Long viewCnt;

    public BoardInfo() {
        this.likeCnt = 0L;
        this.viewCnt = 0L;
    }

    public void addLikeCnt(Long cnt) {
        this.likeCnt += cnt;
    }
    public void subtractLikeCnt(Long cnt) {
        this.likeCnt -= cnt;
    }
}
