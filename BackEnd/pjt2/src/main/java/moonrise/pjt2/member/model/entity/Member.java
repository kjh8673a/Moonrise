package moonrise.pjt2.member.model.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id")
    private Profile profile;


    public void addProfile(Profile memberProfile) {
        this.profile = memberProfile;
    }

    public void addId(Long userId) {
        this.id = userId;
    }
}
