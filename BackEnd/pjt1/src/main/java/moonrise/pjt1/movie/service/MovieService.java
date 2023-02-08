package moonrise.pjt1.movie.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.commons.response.ResponseDto;
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

    public ResponseDto insertMovie(MovieInsertRequestDto requestDto){
        //requestDto에 Movie Builder가 있음!!
        Movie movie = requestDto.movieBuilder();
        ResponseDto responseDto = new ResponseDto();
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setResponseDto(movie);

        //movie 있는지 검사??
        Optional<Movie> findMovieOptinal = movieRepository.findById(movie.getId());

        //영화 데이터가 없다면 저장 후 기존 데이터 반환
        if(!findMovieOptinal.isPresent()){
            movieRepository.save(movie);
        }
        //응답 데이터
        responseDto.setStatus_code(200);
        responseDto.setMessage("성공");
        responseDto.setData(movieResponseDto);

        return responseDto;
    }

    /**
     * 영화 id로 영화 찾기
     */
    public ResponseDto findMovie(Long movieId){
        ResponseDto responseDto = new ResponseDto();
        MovieResponseDto movieResponseDto = new MovieResponseDto();

        Optional<Movie> movie = movieRepository.findById(movieId);
        if(!movie.isPresent()) {
            responseDto.setStatus_code(400);
            responseDto.setMessage("영화가 존재하지 않습니다.");
        }

        Movie m = movie.get();
        movieResponseDto.setResponseDto(m);

        // 응답 데이터
        responseDto.setStatus_code(200);
        responseDto.setData(movieResponseDto);
        responseDto.setMessage("영화 반환~");
        return responseDto;
    }
}
