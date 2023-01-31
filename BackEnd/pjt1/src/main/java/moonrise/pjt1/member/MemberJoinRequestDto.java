package moonrise.pjt1.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class MemberJoinRequestDto {
    private Long user_id;
    private String username;
    private String nickname;
    private String phone;
    private String gender;

}
