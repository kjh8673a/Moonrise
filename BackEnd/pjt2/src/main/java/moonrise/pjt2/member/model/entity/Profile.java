package moonrise.pjt2.member.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long profile_id;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String profile_image_path;


    public Profile(String nickname, String gender) {

        this.nickname = nickname;
        if(gender.equals("M")){
            this.gender = Gender.남;
        }else{
            this.gender = Gender.여;
        }

        //기본 이미지
        this.profile_image_path = "https://moonrise.s3.ap-northeast-2.amazonaws.com/153fcd13-0dab-4757-9f56-6149e253a873_no_image.png";
    }
}
