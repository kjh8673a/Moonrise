package moonrise.pjt1.board.entity;

import moonrise.pjt1.member.entity.Member;

import javax.persistence.*;

@Entity
@Table(name = "board_comment")
public class BoardComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
}
