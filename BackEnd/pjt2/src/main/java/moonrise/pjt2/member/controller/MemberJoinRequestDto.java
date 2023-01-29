package moonrise.pjt2.member.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class MemberJoinRequestDto {
    private String nickname;
    private String phone;

}
