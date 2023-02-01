package moonrise.pjt1.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.member.entity.Member;

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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name="group_id")
    private Long groupId = id;
    private int isNestedComment;
    private LocalDateTime writeDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public static BoardComment createBoardComment(BoardCommentCreateDto boardCommentCreateDto, Board board, Member member) {
        BoardComment boardComment = new BoardComment();
        boardComment.setContent(boardCommentCreateDto.getContent());
        boardComment.setIsNestedComment(boardCommentCreateDto.getIsNestedComment());
        boardComment.setGroupId(boardCommentCreateDto.getGroupId());
        boardComment.setBoard(board);
        boardComment.setMember(member);
        boardComment.setWriteDate(LocalDateTime.now());
        return boardComment;
    }

    public void addBoard(Board board){
        this.board = board;
        board.getBoardComments().add(this);
    }

}
