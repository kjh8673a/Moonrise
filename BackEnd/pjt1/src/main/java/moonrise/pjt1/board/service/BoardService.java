package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardDetailDto;
import moonrise.pjt1.board.dto.BoardListResponseDto;
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
        List<BoardListResponseDto> boardList = boardRepository.findBoardList(movieId);
        System.out.println(boardList.getClass());
        result.put("findBoards", boardList);

//        if(!findMovie.isPresent()){
//            throw new IllegalStateException("존재 하지 않는 영화입니다.");
//        }
//        if(!findboards.isEmpty()){
//            throw new IllegalStateException("게시글 목록이 없습니다.");
//        }
//        result.put("findBoards",findboards);
        return result;
    }

    public Map<String, Object> detailBoard(Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Map<String, Object> result = new HashMap<>();
//        if (findBoard.isPresent()){
//            result.put("findBoard",findBoard.get());
//        }
//        return result;
        if(!findBoard.isPresent()) throw new IllegalStateException("존재하지 않는 게시글 입니다");

        Board board = findBoard.get();
        // 이거 왜 안되는지?
        System.out.println( board.getMember().getProfile());
        //.getProfile().getUsername();
//        if (name.isBlank()){
//            System.out.println("이름이 없다");
//        }
//        System.out.println(name);

      //  BoardDetailDto boardDetailDto = new BoardDetailDto(board.getMember().getId(), board.getMovie().getId(), board.getTitle(), board.getContent(), board.getDateTime(), name);
        ///result.put("findBoard", boardDetailDto);

        return result;
    }

<<<<<<< HEAD
    public Long createBoard(BoardDetailDto boardDetailDto) {

        Optional<Member> findMember = memberRepository.findById(boardDetailDto.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(boardDetailDto.getMovieId());

        if(!findMovie.isPresent()){
            throw new IllegalStateException("존재 하지 않는 영화입니다.");
        }
        if (!findMember.isPresent()){
            throw new IllegalStateException("존재 하지 않는 멤버입니다.");
        }
        // 게시글 정보 생성
        //BoardInfo boardInfo = new BoardInfo("");
//        boardInfo.setBoardStatus(BoardStatus.NORMAL);
//        boardInfo.setCommentCnt(0);
//        boardInfo.setViewCnt(0);
//        boardInfo.setLikeCnt(0);

        // e

        Board board = Board.createBoard(boardDetailDto, findMember.get(), findMovie.get());

        boardRepository.save(board);
        return board.getId();
    }
=======
//    public Long createBoard(BoardCreateDto boardCreateDto) {
//
//        Optional<Member> findMember = memberRepository.findById(boardCreateDto.getMemberId());
//        Optional<Movie> findMovie = movieRepository.findById(boardCreateDto.getMovieId());
//        // 게시글 정보 생성
//        BoardInfo boardInfo = new BoardInfo();
//        boardInfo.setBoardStatus(BoardStatus.NORMAL);
//        boardInfo.setCommentCnt(0);
//
//        Board board = Board.createBoard(boardCreateDto, findMember.get(), findMovie.get());
//        boardRepository.save(board);
//        return board.getId();
//
//    }
>>>>>>> daf06450ded54db8b51c4ad53cf423a9e4b0c2c9
}
