package moonrise.pjt1.member.entity;

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
    @Column(name = "user_name")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    private Gender gender;
    private String phone;

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
    private Member member;

    private String profile_image_path;

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public Profile(String username, String nickname, String gender, String phone) {
        this.username = username;
        this.nickname = nickname;
        if(gender.equals("M")){
            this.gender = Gender.남;
        }else{
            this.gender = Gender.여;
        }
        this.phone = phone;
    }
}
