package moonrise.pjt1.member.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.party.entity.Party;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "memberInfo_id")
    private MemberInfo memberInfo;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.NORMAL;    // default

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Party> parties = new ArrayList<>();

    public void addProfile(Profile memberProfile) {
        this.profile = memberProfile;
    }
    public void addMemberInfo(MemberInfo memberInfo){
        this.memberInfo = memberInfo;
    }
    public void addId(Long userId) {
        this.id = userId;
    }
}