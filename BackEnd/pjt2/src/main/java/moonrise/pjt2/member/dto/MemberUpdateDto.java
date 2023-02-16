package moonrise.pjt2.member.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberUpdateDto {

    private String access_token;
    private String refresh_token;
    private String nickname;

    private String imagePath;
    private List<String> genres;
}
