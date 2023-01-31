package moonrise.pjt1.movie.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.movie.MovieInsertRequestDto;
import moonrise.pjt1.movie.MovieResponseDto;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void insertMovie(MovieInsertRequestDto requestDto){
        //requestDto에 Movie Builder가 있음!!
        Movie movie = requestDto.movieBuilder();

        //movie 있는지 검사??

        movieRepository.save(movie);
    }
    public MovieResponseDto findMovie(Long movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(!movie.isPresent()) return null;

        Movie m = movie.get();
        MovieResponseDto responseDto = new MovieResponseDto();
        responseDto.setResponseDto(m);

        return responseDto;
    }
}
