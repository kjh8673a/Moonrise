package moonrise.pjt2.member.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MemberInfo {
    @Id @GeneratedValue
    @Column(name = "memberInfo_id")
    private Long memberInfo_id;

    private int bannedCnt;

    private String likeMovie;
    private String likeGenre;
    private String likeBoard;
    private String bookmarkBoard;
    private String likeParty;
    private String bookmarkParty;

    public MemberInfo() {
        this.bannedCnt = 0;
        this.likeMovie = "";
        this.likeGenre = "";
        this.likeBoard = "";
        this.bookmarkBoard = "";
        this.likeParty = "";
        this.bookmarkParty = "";
    }
}
