package moonrise.pjt2.member.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

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
