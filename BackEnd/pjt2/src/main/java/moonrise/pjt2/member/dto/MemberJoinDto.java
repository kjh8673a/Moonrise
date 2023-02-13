package moonrise.pjt2.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class MemberJoinDto {
    private String access_token;
    private String refresh_token;
    private String nickname;
    private String gender;

    private String imagePath;
    private List<String> genres;
}
