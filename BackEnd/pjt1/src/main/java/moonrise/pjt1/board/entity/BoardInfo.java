package moonrise.pjt1.board.entity;

import javax.persistence.*;

@Entity
@Table(name = "board_status")
public class BoardInfo {
    @Id @GeneratedValue
    @Column(name = "board_info_id")
    private Long id;

    @OneToOne(mappedBy = "boardInfo",fetch = FetchType.LAZY)
    private Board board;

    private BoardStatus boardStatus;
    private int likeCnt;
    private int viewCnt;
    private int commentCnt;
}
