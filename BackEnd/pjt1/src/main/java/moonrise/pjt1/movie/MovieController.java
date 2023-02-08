package moonrise.pjt1.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    @Autowired
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovieInsertRequestDto requestDto){
        log.info("movieDto : {}", requestDto);

        ResponseDto responseDto = movieService.insertMovie(requestDto);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovie(@PathVariable Long movieId){
        log.info("movieId : {}", movieId);

        ResponseDto responseDto = movieService.findMovie(movieId);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
