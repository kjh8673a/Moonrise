package moonrise.pjt1.movie.service;

import moonrise.pjt1.movie.MovieInsertRequestDto;
import moonrise.pjt1.movie.MovieResponseDto;
import moonrise.pjt1.movie.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceTest {
    @Autowired
    MovieService movieService;

    @Test
    void 영화넣기(){
        MovieInsertRequestDto requestDto = new MovieInsertRequestDto();
        requestDto.setId(100402L);
        requestDto.setTitle("캡틴 아메리카");
        requestDto.setOriginalTitle("Captain America: The Winter Soldier");
        requestDto.setPopularity(66.807);

        movieService.insertMovie(requestDto);

        MovieResponseDto movie = (MovieResponseDto) movieService.findMovie(requestDto.getId()).getData();
        Assertions.assertEquals(requestDto.getId(), movie.getId());
    }
    @Test
    @Transactional
    void 영화검색(){
        //given
        Long movieId = 100402L;

        //when
        MovieResponseDto movie = (MovieResponseDto) movieService.findMovie(movieId).getData();

        //then
        Assertions.assertEquals(movieId, movie.getId());
    }


}