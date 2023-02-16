package moonrise.pjt3.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt3.board.dto.BoardCommentCreateDto;
import moonrise.pjt3.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_comment")
@Getter
@Setter
@NoArgsConstructor
public class BoardComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name="group_id")
    private Long groupId;

    private LocalDateTime writeDate;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public static BoardComment createBoardComment(BoardCommentCreateDto boardCommentCreateDto, Board board, Member member) {
        BoardComment boardComment = new BoardComment();
//        boardComment.setContent();
//        boardComment.setBoard();
//        boardComment.setGroupId(0);

        return boardComment;
    }

    public void addBoard(Board board){
        this.board = board;
        board.getBoardComments().add(this);
    }

}
