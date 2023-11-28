package moonrise.pjt1.rating.controller;

import lombok.extern.slf4j.Slf4j;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.request.RatingCreateReq;
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

    @PostMapping("/create")
    public ResponseEntity createRating(@RequestBody RatingCreateReq dto) {
        ResponseDto responseDto = ratingService.createRating(dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/find/{movieId}")
    public ResponseEntity findRating(@RequestParam(name="memberId", defaultValue = "0", required = false) Long memberId, @PathVariable("movieId") Long movieId) {
        ResponseDto responseDto = ratingService.findRating(memberId, movieId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}