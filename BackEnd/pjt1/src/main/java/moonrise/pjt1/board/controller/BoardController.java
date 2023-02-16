package moonrise.pjt1.board.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.board.dto.*;
import moonrise.pjt1.board.service.BoardService;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.util.GetResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {
    private final BoardService boardService;

    // 게시글 목록보기 (0순위)
    @GetMapping(value = "/list/{movieId}")
    public ResponseEntity<?>boardList(@PathVariable("movieId") Long movieId,
                                                        @RequestParam(value = "page", defaultValue = "0") int page){
        PageRequest pageable = PageRequest.of(page, 3, Sort.by("id").descending());
        ResponseDto responseDto = boardService.listBoard(movieId, pageable);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }


    // 게시글 상세보기 (0순위)
    @GetMapping("/{boardId}")
    public ResponseEntity<?> boardDetail(@RequestHeader HttpHeaders headers,
                                         @PathVariable("boardId") Long boardId){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardService.detailBoard(access_token, boardId);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    // 게시글 생성 (0순위)
    @PostMapping("/create")
    public ResponseEntity<?> boardCreate(@RequestHeader HttpHeaders headers,
                                         @RequestBody BoardCreateDto boardCreateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardService.createBoard(access_token, boardCreateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    // 게시글 수정 (0순위)
    @PutMapping("/modify")
    public ResponseEntity<?> boardUpdate(@RequestHeader HttpHeaders headers,
                                         @RequestBody BoardUpdateDto boardUpdateDto){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardService.updateBoard(access_token, boardUpdateDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    // 글 상태바꾸기 ( 삭제, 신고, 평범)
    // 1:normal 2: banned 3: deleted
    @PostMapping("/status")
    public ResponseEntity<?> boardChangeStatus(@RequestHeader HttpHeaders headers,
                                  @RequestParam(name="boardId") Long boardId,
                                  @RequestParam(name="statusCode") int statusCode){
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        ResponseDto responseDto = boardService.statusBoard(access_token, boardId, statusCode);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }


    // 게시글 좋아요 (1순위)
    @PostMapping("/like")
    public ResponseEntity<?> boardLike(@RequestHeader HttpHeaders headers, @RequestBody BoardLikeDto boardLikeDto){
        String access_token = headers.get("access_token").toString();
        ResponseDto responseDto = boardService.likeBoard(access_token, boardLikeDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    // 게시글 인기목록 (1순위)
    // 게시글 북마크 (1순위)
    @PostMapping("/bookmark")
    public ResponseEntity<?> boardBookmark(@RequestHeader HttpHeaders headers, @RequestBody BoardBookmarkDto boardBookmarkDto){
        String access_token = headers.get("access_token").toString();
        ResponseDto responseDto = boardService.bookmarkBoard(access_token, boardBookmarkDto);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    // 마이페이지 내 북마크 글 목록받기
    @GetMapping("/mypage/bookmark")
    public ResponseEntity<?> mypageBookmark(@RequestHeader HttpHeaders headers) {
        String access_token = headers.get("access_token").toString();
        ResponseDto responseDto = boardService.bookmarkMypage(access_token);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    // 마이페이지 내가 작성한 글 목록받기
    @GetMapping("/mypage/board")
    public ResponseEntity<?> mypageBoard(@RequestHeader HttpHeaders headers) {
        String access_token = headers.get("access_token").toString();
        ResponseDto responseDto = boardService.boardMypage(access_token);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/test")
    public void test(String token){
        System.out.println("token Test : " + token);
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
            System.out.println("responseCode = " + responseCode);

            if(responseCode == 200){    // 유효성 통과
                System.out.println("성공");
                JsonObject jsonObject = GetResponse.getJsonResponse(conn).getAsJsonObject();
                System.out.println(jsonObject.toString());

                JsonElement data = jsonObject.get("data");
                System.out.println(data.getAsJsonObject().get("user_id"));

                return;
            }
            System.out.println("에러~");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
