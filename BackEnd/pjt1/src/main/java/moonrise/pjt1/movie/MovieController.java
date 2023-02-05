package moonrise.pjt1.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonrise.pjt1.movie.exception.MovieExistException;
import moonrise.pjt1.movie.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

@RestController
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    @Autowired
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovieInsertRequestDto requestDto){
        log.debug("movieDto : {}", requestDto);

        try {
            movieService.insertMovie(requestDto);
        }catch (MovieExistException e){
            // 이미 존재하는 영화
            log.error(e.getMessage());
            return new ResponseEntity<Null>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Null>(HttpStatus.OK);
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovie(@PathVariable Long movieId){
        log.info("movieId : {}", movieId);

        MovieResponseDto movie = movieService.findMovie(movieId);
        //null 처리 해야함.
        return new ResponseEntity<MovieResponseDto>(movie, HttpStatus.OK);
    }
}
