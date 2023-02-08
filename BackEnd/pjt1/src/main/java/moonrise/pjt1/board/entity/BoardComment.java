package moonrise.pjt1.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

import static moonrise.pjt1.board.entity.BoardCommentStatus.*;

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

    @Enumerated(EnumType.STRING)
    private BoardCommentStatus boardCommentStatus = NORMAL;

    private String content;
    private String writer;

    public static BoardComment createBoardComment(BoardCommentCreateDto boardCommentCreateDto, Board board, Member member) {
        BoardComment boardComment = new BoardComment();
        boardComment.setContent(boardCommentCreateDto.getContent());
        boardComment.setIsNestedComment(boardCommentCreateDto.getIsNestedComment());
        boardComment.setGroupId(boardCommentCreateDto.getGroupId());
        boardComment.setBoard(board);
        boardComment.setMember(member);
        boardComment.setWriter(member.getProfile().getNickname());
        boardComment.setWriteDate(LocalDateTime.now());
        return boardComment;
    }

    public void addBoard(Board board){
        this.board = board;
        board.getBoardComments().add(this);
    }

    public void normalize() { this.setBoardCommentStatus(NORMAL);
    }

    public void banned() { this.setBoardCommentStatus(BANNED);}

    public void deleted() { this.setBoardCommentStatus(DELETED);}
}
