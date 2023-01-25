package moonrise.pjt1.board.entity;

import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_comment")
public class BoardComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public void addBoard(Board board){
        this.board = board;
        board.getBoardComments().add(this);
    }

}
