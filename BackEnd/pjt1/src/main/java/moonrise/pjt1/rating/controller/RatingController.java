package moonrise.pjt1.rating.controller;

import lombok.extern.slf4j.Slf4j;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.repository.RatingRepository;
import moonrise.pjt1.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    public ResponseEntity createRating(@PathVariable long movieId, @RequestBody RatingDto ratingDto) {
        long total = ratingDto.getStory() + ratingDto.getActing() + ratingDto.getSound() + ratingDto.getVisual() + ratingDto.getDirection() / 5;
        RatingDto dto = RatingDto.builder()
                .story(ratingDto.getStory())
                .acting(ratingDto.getActing())
                .sound(ratingDto.getSound())
                .visual(ratingDto.getVisual())
                .direction(ratingDto.getDirection())
                .total(total)
                .movieId(ratingDto.getMovieId())
                .memberId(ratingDto.getMemberId())
                .build();
        ratingService.createRating(movieId, ratingDto.getMovieId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity updateRating(@PathVariable long ratingId,  @RequestBody RatingDto ratingDto) {
        long total = ratingDto.getStory() + ratingDto.getActing() + ratingDto.getSound() + ratingDto.getVisual() + ratingDto.getDirection() / 5;
        ratingDto = RatingDto.builder()
                .story(ratingDto.getStory())
                .acting(ratingDto.getActing())
                .sound(ratingDto.getSound())
                .visual(ratingDto.getVisual())
                .direction(ratingDto.getDirection())
                .total(total)
                .movieId(ratingDto.getMovieId())
                .memberId(ratingDto.getMemberId())
                .build();
        ratingService.updateRating(ratingId, ratingDto);
        return ResponseEntity.status(HttpStatus.OK).body(ratingDto);
    }

    @GetMapping("find/{movieId}")
    public ResponseEntity findRating(@PathVariable long movieId) {
        List<Long> result=ratingService.findRating(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
