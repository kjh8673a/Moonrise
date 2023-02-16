package moonrise.pjt1.rating.controller;

import lombok.extern.slf4j.Slf4j;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.service.RatingService;
import moonrise.pjt1.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/create/{movieId}")
    public ResponseEntity createRating(@RequestHeader HttpHeaders headers, @PathVariable long movieId, @RequestBody RatingDto ratingDto) {
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        Long memberId = HttpUtil.requestParingToken(access_token);
        long total = ratingDto.getStory() + ratingDto.getActing() + ratingDto.getSound() + ratingDto.getVisual() + ratingDto.getDirection();
        RatingDto dto = RatingDto.builder()
                .story(ratingDto.getStory())
                .acting(ratingDto.getActing())
                .sound(ratingDto.getSound())
                .visual(ratingDto.getVisual())
                .direction(ratingDto.getDirection())
                .total(total)
                .movieId(movieId)
                .memberId(memberId)
                .build();
        ratingService.createRating(movieId, memberId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity updateRating(@RequestHeader HttpHeaders headers, @PathVariable long ratingId, @RequestBody RatingDto ratingDto) {
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        Long memberId = HttpUtil.requestParingToken(access_token);
        long total = ratingDto.getStory() + ratingDto.getActing() + ratingDto.getSound() + ratingDto.getVisual() + ratingDto.getDirection();
        ratingDto = RatingDto.builder()
                .story(ratingDto.getStory())
                .acting(ratingDto.getActing())
                .sound(ratingDto.getSound())
                .visual(ratingDto.getVisual())
                .direction(ratingDto.getDirection())
                .total(total)
                .movieId(ratingDto.getMovieId())
                .memberId(memberId)
                .build();
        ratingService.updateRating(ratingId, ratingDto);
        return ResponseEntity.status(HttpStatus.OK).body(ratingDto);
    }

    @GetMapping("find/{movieId}")
    public ResponseEntity findRating(@PathVariable long movieId) {
        if (ratingService.findRating(movieId) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            List<Long> result = ratingService.findRating(movieId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @GetMapping("personal")
    public ResponseEntity findPersonal(@RequestHeader HttpHeaders headers, @RequestParam long movieId) {
        // Http Header 에서 Access-Token 받기
        String access_token = headers.get("access_token").toString();
        log.info("access_token : {}", access_token);
        Long memberId = HttpUtil.requestParingToken(access_token);
        //없는 사람 null
        if (ratingService.findPersonal(movieId, memberId) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            List<Long> result = ratingService.findPersonal(movieId, memberId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
}