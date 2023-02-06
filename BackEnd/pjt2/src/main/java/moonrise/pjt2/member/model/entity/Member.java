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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.NORMAL;    // default

    public void addProfile(Profile memberProfile) {
        this.profile = memberProfile;
    }

    public void addId(Long userId) {
        this.id = userId;
    }
}
