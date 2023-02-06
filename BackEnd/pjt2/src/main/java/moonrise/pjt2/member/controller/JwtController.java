package moonrise.pjt2.member.controller;

import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import moonrise.pjt2.member.exception.UnauthorizedException;
import moonrise.pjt2.util.HttpUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/jwt")
public class JwtController {
    /**
     * access-Token 가지고 아이디 요청
     * 다른 서버에서 access-Token을 가지고 userId를 얻기위해 사용
     * access-Token 만료시, 401 Error
     */
    @PostMapping("/parse")
    public ResponseEntity<?> parseAccessToken(@RequestHeader HttpHeaders headers){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access-Token : {}", access_token);

        ResponseDto responseDto = new ResponseDto();
        HashMap<String, Object> resultMap = new HashMap<>();
        try{
            HashMap<String, Object> userInfo = HttpUtil.parseToken(access_token);
            Long userId = (Long) userInfo.get("user_id");
            log.info("parse result : {}", userId);
            resultMap.put("user_id", userId);

            responseDto.setStatus_code(200);
            responseDto.setMessage("토큰 파싱 완료");
            responseDto.setData(resultMap);

            return ResponseEntity.ok().body(responseDto);
        }catch (UnauthorizedException e){
            log.error(e.getMessage());

            return ResponseEntity.status(401).body(null);
        }
    }
    /**
     * Refresh-Token을 받아 access-Token을 재발급 받는다.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader HttpHeaders headers){
        // Http Header 에서 Refresh-Token 받기
        String refresh_token = headers.get("refresh_token").toString();
        log.info("refresh-token : {}", refresh_token);

        String requestURL = "https://kauth.kakao.com/oauth/token";
        HashMap<String, Object> resultMap = new HashMap<>();
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
            sb.append("&refresh_token=" + refresh_token); // TODO 인가코드 받은 redirect_uri 입력

            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = connection.getResponseCode();

            if(responseCode == 200) {
                //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
                JsonElement element = GetResponse.getJsonResponse(connection);

                String new_Access_Token = element.getAsJsonObject().get("access_token").getAsString();
                String new_Refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

                log.info("access_token : {}", new_Access_Token);
                log.info("refresh_token : {}", new_Refresh_Token);

                resultMap.put("access_token", new_Access_Token);
                resultMap.put("refresh_token", new_Refresh_Token);

                ResponseDto responseDto = new ResponseDto();
                responseDto.setStatus_code(200);
                responseDto.setMessage("Token 재발급 성공");
                responseDto.setData(resultMap);

                return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus_code(400);
        responseDto.setMessage("Refresh Token 만료 : 재 로그인");

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
