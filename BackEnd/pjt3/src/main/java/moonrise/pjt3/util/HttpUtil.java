package moonrise.pjt3.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpUtil {
    public static Long requestParingToken(String token){
        String requestUrl = "http://pjt2-container:9002/auth/jwt/parse";
        //String requestUrl = "http://localhost:9002/auth/jwt/parse";

        try{
            URL url = new URL(requestUrl);  // URL 객체

            // KAKAO 서버에 HTTP 요청
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("access_token", token);

            // 응답 코드
            int responseCode = conn.getResponseCode();

            // success : 200, 유효성 error : 401
            log.info("responseCode = " + responseCode);

            if(responseCode == 200){    // 유효성 통과
                JsonObject jsonObject = GetResponse.getJsonResponse(conn).getAsJsonObject();

                JsonElement data = jsonObject.get("data");
                Long user_id = Long.parseLong(data.getAsJsonObject().get("user_id").toString());
                log.info("user_id : {}", user_id);

                return user_id;
            }
            System.out.println("에러~");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
