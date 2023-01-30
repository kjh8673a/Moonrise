package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardDto;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardInfo;
import moonrise.pjt1.board.entity.BoardStatus;
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

    public Long createBoard(BoardDto boardDto) {

        Optional<Member> findMember = memberRepository.findById(boardDto.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(boardDto.getMovieId());
        // 게시글 정보 생성
        BoardInfo boardInfo = new BoardInfo();
        boardInfo.setBoardStatus(BoardStatus.NORMAL);
        boardInfo.setCommentCnt(0);

        Board board = Board.createBoard(boardDto, findMember.get(), findMovie.get(), boardInfo);
        boardRepository.save(board);
        return board.getId();

    }
}
