package moonrise.pjt2.util;

import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import moonrise.pjt2.member.controller.GetResponse;
import moonrise.pjt2.member.exception.UnauthorizedException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Slf4j
public class HttpUtil {
    public static HashMap<String, Object> parseToken(String token){
        String requestUrl = "https://kapi.kakao.com/v2/user/me";

        try{
            URL url = new URL(requestUrl);  // URL 객체

            // KAKAO 서버에 HTTP 요청
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            // 응답 코드
            int responseCode = conn.getResponseCode();

            // success : 200, 유효성 error : 401
            log.info("responseCode =" + responseCode);

            if(responseCode == 200){    // 유효성 통과

                JsonElement element = GetResponse.getJsonResponse(conn);

                Long id = Long.parseLong(element.getAsJsonObject().get("id").getAsString());
                log.info("user_id : {}", id);
                //String nickname = element.getAsJsonObject().get("profile_nickname").getAsString();

                HashMap<String, Object> userInfo = new HashMap<>();
                userInfo.put("user_id",id);
                //userInfo.put("nickname", nickname);

                return userInfo;
            }
            else if(responseCode == 401){   //access-Token 만료 시
                throw new UnauthorizedException("토큰 만료..");
            }

        } catch (IOException ioException) {

        }
        return null;
    }
}
