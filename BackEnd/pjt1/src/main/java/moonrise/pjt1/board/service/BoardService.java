package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.controller.BoardDetailDto;
import moonrise.pjt1.board.controller.BoardForm;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;

    public Map<String, Object> listBoard(Long movieId){
        Optional<Movie> findMovie = movieRepository.findById(movieId);
        Map<String,Object> result = new HashMap<>();
        if(findMovie.isPresent()){
            result.put("findBoards",findMovie.get().getBoards());
        }
        return result;
    }

    public Map<String, Object> detailBoard(Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Map<String, Object> result = new HashMap<>();
        if (findBoard.isPresent()){
            result.put("findBoard",findBoard.get());
        }
        return result;
    }

    public Long createBoard(BoardForm boardForm) {
        Optional<Member> findMember = memberRepository.findById(boardForm.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(boardForm.getMovieId());

        Board board = Board.createBoard(boardForm, findMember.get(), findMovie.get());
        boardRepository.save(board);
        return board.getId();

    }
}
