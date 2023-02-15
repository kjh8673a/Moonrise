package moonrise.pjt3.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private Member member;

    private String profile_image_path;

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public Profile(String nickname, String gender) {
        this.nickname = nickname;
        if(gender.equals("M")){
            this.gender = Gender.남;
        }else{
            this.gender = Gender.여;
        }
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", member=" + member +
                ", profile_image_path='" + profile_image_path + '\'' +
                '}';
    }
}
