package moonrise.pjt2.member.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter
public class MemberJoinDto {
    private String access_token;
    private String refresh_token;
    private String nickname;
    private String gender;
    private List<String> genres;
}
