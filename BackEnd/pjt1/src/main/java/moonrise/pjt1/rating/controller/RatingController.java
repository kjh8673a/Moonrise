package moonrise.pjt1.rating.controller;

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
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/create/{movieId}")
    public ResponseEntity createRating(@PathVariable long movieId, long story, long acting, long direction, long visual,
                                       long sound, long memberId) {
        long total = story + acting + direction + visual + sound / 5;
        RatingDto dto = RatingDto.builder()
                .story(story)
                .acting(acting)
                .sound(sound)
                .visual(visual)
                .direction(direction)
                .total(total)
                .movieId(movieId)
                .memberId(memberId)
                .build();
        ratingService.createRating(movieId, memberId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity updateRating(@PathVariable long ratingId, long story, long acting, long direction, long visual,
                                       long sound, long memberId, long movieId) {
        long total = story + acting + direction + visual + sound / 5;
        RatingDto dto = RatingDto.builder()
                .story(story)
                .acting(acting)
                .sound(sound)
                .visual(visual)
                .direction(direction)
                .total(total)
                .movieId(movieId)
                .memberId(memberId)
                .build();
        ratingService.updateRating(ratingId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("find/{movieId}")
    public ResponseEntity findRating(@PathVariable long movieId) {
        List<Long> result=ratingService.findRating(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
