package moonrise.pjt2.member.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonrise.pjt2.member.dto.MemberJoinDto;
import moonrise.pjt2.member.dto.MemberUpdateDto;
import moonrise.pjt2.member.dto.ResponseDto;
import moonrise.pjt2.member.exception.UnauthorizedException;
import moonrise.pjt2.member.model.service.MemberService;

import moonrise.pjt2.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    /**
     *
     * KaKao 서버로 부터 인가 코드를 받아
     * Access-Token 과 Refresh-Token을 받는다.
     */
    @Value("${kakao.url.code}")
    private String get_token_url;

    @Value("${client_id}")
    private String client_id;

    @Value("${redirect_uri}")
    private String redirect_uri;

    private final HttpUtil httpUtil;

    @PostMapping("/kakao")
    @Transactional
    public ResponseEntity<?> getKaKaoToken(@RequestHeader HttpHeaders headers){
        ResponseDto responseDto = new ResponseDto();
        try{
            // Http Header 에서 인가 코드 받기
            String authorization_code = headers.get("authorization_code").toString();

            log.info("auth_code : {}", authorization_code);

            String access_Token = "";
            String refresh_Token = "";

            HashMap<String, Object> resultMap = new HashMap<>();

            String requestURL = get_token_url;

            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // POST 요청에 필요로 요고하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + client_id); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=" + redirect_uri); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&prompt=login");
            sb.append("&code=" + authorization_code);
            bw.write(sb.toString());
            bw.flush();

            log.info(sb.toString());
            //결과 코드가 200이라면 성공
            int responseCode = connection.getResponseCode();
            log.info("get_token_res_code : {}", responseCode);

            if(responseCode == 400){
                //Error
                responseDto.setMessage("인가 코드로 토큰 받는 과정에서 오류");
                responseDto.setStatus_code(400);
                return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
            }

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("access_token : {}", access_Token);
            log.info("refresh_token : {}", refresh_Token);

            //access-token을 파싱 하여 카카오 id가 디비에 있는지 확인
            Long user_id = httpUtil.parseToken(access_Token);
            log.info("parse result : {}", user_id);

            if(user_id == null){
                log.error("userId == null error");
                responseDto.setStatus_code(400);
                responseDto.setMessage("토큰 파싱과정에서 오류");
                return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
            }

            if(memberService.check_enroll_member(user_id)){  // 회원가입해
                resultMap.put("access_token",access_Token);
                resultMap.put("refresh_token",refresh_Token);

                responseDto.setStatus_code(400);
                responseDto.setMessage("회원가입 정보 없음!!");
                responseDto.setData(resultMap);

                return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);  //200
            }
            // 회원가입 되어 있어 회원 정보 반환해.
            MemberJoinDto dto = memberService.findMemberAll(user_id);
            dto.setAccess_token(access_Token);
            dto.setRefresh_token(refresh_Token);

            responseDto.setStatus_code(200);
            responseDto.setMessage("로그인 완료!!");
            responseDto.setData(dto);

            br.close();
            bw.close();
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().body(responseDto);
    }

    @Value("${kakao_logout_url}")
    private String kakao_logout_url;

    /**
     * 카카오 계정과 함께 로그아웃
     * 웹 브라우저에 로그인된 카카오계정의 세션을 만료시키고, 서비스에서도 로그아웃 처리할 때 사용하는 로그아웃 추가 기능
     * 기본적인 로그아웃은 토큰을 만료시켜 해당 사용자 정보로 더 이상 카카오 API를 호출할 수 없도록 하는 기능
     * 카카오계정 로그아웃 처리 후 Logout Redirect URI로 302 리다이렉트(Redirect)
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader HttpHeaders headers){
        ResponseDto responseDto = new ResponseDto();
        try{
            String access_token = headers.get("access_token").toString();

            URL url = new URL(kakao_logout_url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization","Bearer " + access_token);

            // 응답 코드
            int responseCode = conn.getResponseCode();
            log.info("logout_res_code : {}", responseCode);

            responseDto.setStatus_code(200);
            responseDto.setMessage("로그아웃 완료");

            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus_code(400);
            responseDto.setMessage("로그아웃 실패.. 그냥 로그아웃 해..");

            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers, @RequestBody MemberUpdateDto memberUpdateDto){
        log.info("memberUpdateDto : {}", memberUpdateDto.toString());

        String access_token = headers.get("access_token").toString();
        ResponseDto responseDto = memberService.memberInfoUpdate(access_token, memberUpdateDto);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestHeader HttpHeaders headers, @RequestBody MemberJoinDto memberJoinDto){
        log.info("memberJoin Data : {}", memberJoinDto);

        String access_token = headers.get("access_token").toString();
        String refresh_token = headers.get("refresh_token").toString();
        log.info("join_access_token : {}", access_token);

        // token을 통해 userid 받아오기
        Long user_id = HttpUtil.parseToken(access_token);

        ResponseDto responseDto = new ResponseDto();
        if(user_id == null){
            log.error("userId == null error");

            responseDto.setStatus_code(400);
            responseDto.setMessage("토큰 파싱과정에서 오류");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        memberJoinDto.setAccess_token(access_token);
        memberJoinDto.setRefresh_token(refresh_token);

        // Service에 요청
        responseDto = memberService.join(memberJoinDto, user_id);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/check")
    public ResponseEntity<?> nicknameCheck(@RequestParam String nickname){
        ResponseDto responseDto = memberService.nicknameCheck(nickname);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
