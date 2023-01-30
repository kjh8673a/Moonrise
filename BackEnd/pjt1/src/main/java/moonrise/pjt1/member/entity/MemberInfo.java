package moonrise.pjt1.member.entity;

import lombok.Getter;
import lombok.Setter;
import moonrise.pjt1.board.entity.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Getter @Setter
//public class MemberInfo {
//    @Id @GeneratedValue
//    @Column(name = "member_info_id")
//    private Long id;
//
//    @OneToOne(mappedBy = "memberInfo",fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    private MemberStatus memberStatus;
//    private int bannedCnt;
//
//    private String likeMovie;
//    private String likeGenre;
//    private String likeBoard;
//    private String bookmarkBoard;
//    private String likeParty;
//    private String bookmarkParty;
//}
