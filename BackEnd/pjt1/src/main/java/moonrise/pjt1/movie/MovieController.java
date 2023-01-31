package moonrise.pjt1.movie;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MovieController {

    @Autowired
    private final MovieService movieService;
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovieInsertRequestDto movieDto){
        logger.info("movieDto : {}", movieDto);

        return new ResponseEntity<Null>(HttpStatus.OK);
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovie(@PathVariable Long movieId){
        logger.info("movieId : {}", movieId);
        return new ResponseEntity<Null>(HttpStatus.OK);
    }
}
