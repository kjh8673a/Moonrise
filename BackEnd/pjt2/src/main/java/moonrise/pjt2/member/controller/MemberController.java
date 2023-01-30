package moonrise.pjt2.member.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import moonrise.pjt2.member.model.entity.Profile;
import moonrise.pjt2.member.model.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
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
            sb.append("&prompt=login");
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


//            HashMap<String, Object> userInfo = getUserInfo(access_Token);
//            System.out.println("userInfo = " + userInfo.toString());

            //access-token을 파싱 하여 카카오 id가 디비에 있는지 확인
            Long userId = parseToken(access_Token);
            if(memberService.check_enroll_member(userId)){  // 회원가입해
                resultMap.put("kakaoId",userId);
                return new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.NO_CONTENT);

            }else{  // 회원가입 되어 있어 그냥 token만 반환해
                resultMap.put("access_token", access_Token);
                resultMap.put("refresh_token", refresh_Token);
            }

            br.close();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body(resultMap);
    }

    private Long parseToken(String token) throws Exception{
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
            System.out.println("responseCode =" + responseCode);

            if(responseCode == 200){    // 유효성 통과

                JsonElement element = getJsonResponse(conn);

                Long id = Long.parseLong(element.getAsJsonObject().get("id").getAsString());
                logger.info("kakao_id : {}", id);

                return id;
            }
            else if(responseCode == 401){   //access-Token 만료 시

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * access-Token 가지고 아이디 요청
     * 다른 서버에서 access-Token을 가지고 userId를 얻기위해 사용
     * access-Token 만료시, 401 Error
     */
    @GetMapping("/parse")
    public ResponseEntity<?> parseAccessToken(String accessToken){
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

            // success : 200, 유효성 error : 401
            System.out.println("responseCode =" + responseCode);

            if(responseCode == 200){    // 유효성 통과

                JsonElement element = getJsonResponse(conn);

                String id = element.getAsJsonObject().get("id").getAsString();
                logger.info("kakao_id : {}", id);

                return ResponseEntity.ok().body(id);
            }
            else if(responseCode == 401){   //access-Token 만료 시

                return ResponseEntity.status(401).body(null);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

    private static JsonElement getJsonResponse(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println("response body : " + result);

        JsonParser parser = new JsonParser();
        JsonElement element =  parser.parse(result);
        return element;
    }

    /**
     * Refresh-Token을 받아 access-Token을 재발급 받는다.
     */
    @GetMapping("/refresh")
    public void refresh(String token){
        String refresh_Token = token;

        String requestURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // POST 요청에 필요로 요고하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=refresh_token");
            sb.append("&client_id=f0b916ceedccef620b4f4a6ab4e6bec5"); // TODO REST_API_KEY 입력
            sb.append("&refresh_token=" + refresh_Token); // TODO 인가코드 받은 redirect_uri 입력

            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = connection.getResponseCode();

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            JsonElement element = getJsonResponse(connection);

            String new_Access_Token = element.getAsJsonObject().get("access_token").getAsString();
            String new_Refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            logger.info("access_token : {}", new_Access_Token);
            logger.info("refresh_token : {}", new_Refresh_Token);

//            resultMap.put("access_token", access_Token);
//            resultMap.put("refresh_token", refresh_Token);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping("/logout")
    public void logout(String accessToken){
        String requestUrl = "https://kapi.kakao.com/v1/user/logout";

        try{
            URL url = new URL(requestUrl);

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
            System.out.println("response body =" + result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/logout2")
    public void logout2(String accessToken){
        StringBuilder sb = new StringBuilder();
        sb.append("https://kauth.kakao.com/oauth/logout?");
        sb.append("client_id=" + "f0b916ceedccef620b4f4a6ab4e6bec5");
        sb.append("&logout_redirect_uri="+"http://localhost:9000/auth/member/logout");

        String requestUrl = sb.toString();
        try{
            URL url = new URL(requestUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 응답 코드
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode =" + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body =" + result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        logger.info("memberJoin Data : {}", memberJoinRequestDto);

        // Service에 요청
        memberService.join(memberJoinRequestDto);

        return ResponseEntity.ok().body(null);
    }
}
