package moonrise.pjt2.member.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/member")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    /**
     *
     * KaKao 서버로 부터 인가 코드를 받아
     * Access-Token 과 Refresh-Token을 받는다.
     */
    @GetMapping("/kakao")
    public ResponseEntity<?> getKaKaoToken(String code){
        logger.info("code : {}", code);

        String access_Token = "";
        String refresh_Token = "";
        HashMap<String, Object> resultMap = new HashMap<>();
        String requestURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // POST 요청에 필요로 요고하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=f0b916ceedccef620b4f4a6ab4e6bec5"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:9000/auth/member/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = connection.getResponseCode();
            logger.info("getKaKaoToken :: responseCode : {}", responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            logger.info("access_token : {}", access_Token);
            logger.info("refresh_token : {}", refresh_Token);

            resultMap.put("access_token", access_Token);
            resultMap.put("refresh_Token", refresh_Token);
//            HashMap<String, Object> userInfo = getUserInfo(access_Token);
//            System.out.println("userInfo = " + userInfo.toString());

            br.close();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(resultMap);
    }

    /**
     *
     * access-Token 가지고 아이디 요청
     */
    @GetMapping("/parse")
    public void parseAccessToken(String accessToken){
        logger.info("access-Token : {}", accessToken);

        String requestUrl = "https://kapi.kakao.com/v2/user/me";

        try{
            URL url = new URL(requestUrl);  // URL 객체

            // KAKAO 서버에 HTTP 요청
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            // 응답 코드
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode =" + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body ="+result);

            JsonParser parser = new JsonParser();
            JsonElement element =  parser.parse(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, Object> getUserInfo(String accessToken) throws IOException {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode =" + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body ="+result);

            JsonParser parser = new JsonParser();
            JsonElement element =  parser.parse(result);
//
//            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
//            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//
//            String profile_nickname = properties.getAsJsonObject().get("nickname").getAsString();
//            String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
//            String account_email = kakaoAccount.getAsJsonObject().get("email").getAsString();
//
//            userInfo.put("profile_nickname", profile_nickname);
//            userInfo.put("profile_image", profile_image);
//            userInfo.put("account_email", account_email);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
