package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardCreateDto;
import moonrise.pjt1.board.dto.BoardDetailDto;
import moonrise.pjt1.board.dto.BoardListResponseDto;
import moonrise.pjt1.board.dto.BoardUpdateDto;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardComment;
import moonrise.pjt1.board.entity.BoardInfo;
import moonrise.pjt1.board.repository.BoardCommentRepository;
import moonrise.pjt1.board.repository.BoardInfoRepository;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final BoardInfoRepository boardInfoRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final RedisTemplate redisTemplate;


    public Map<String, Object> listBoard(Long movieId, Pageable pageable){
        Optional<Movie> findMovie = movieRepository.findById(movieId);
        Map<String,Object> result = new HashMap<>();
        if(!findMovie.isPresent()){
            throw new IllegalStateException("존재 하지 않는 영화입니다.");
        }
        Page<Board> boardList = boardRepository.findByMovieId(movieId, pageable);

        List<BoardListResponseDto> findBoards = new ArrayList<>();

        for(Board b : boardList){
            int viewCnt = b.getBoardInfo().getViewCnt();
            int commentsCnt = b.getBoardComments().size();
            int likeCnt = b.getBoardInfo().getLikeCnt();
            String nickname = b.getMember().getProfile().getNickname();
            BoardListResponseDto boardListResponseDto = new BoardListResponseDto(b.getId(),b.getTitle(),b.getContent(),b.getDateTime(),likeCnt,commentsCnt,viewCnt,nickname);
            findBoards.add(boardListResponseDto);
        }
        result.put("findBoards", findBoards);
        result.put("totalPages", boardList.getTotalPages());
        return result;
    }

    public Map<String, Object> detailBoard(Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Map<String, Object> result = new HashMap<>();
        if(!findBoard.isPresent()) throw new IllegalStateException("존재하지 않는 게시글 입니다");
        // cache 조회수 저장 및 증가
        String key = "boardViewCnt::"+boardId;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(valueOperations.get(key)==null){
            valueOperations.set(key, String.valueOf(boardInfoRepository.findBoardViewCnt(boardId)+1),20, TimeUnit.MINUTES);
        }else {
            valueOperations.increment(key);
        }
        int viewCnt = Integer.parseInt((String) valueOperations.get(key));
        Board board = findBoard.get();
        String writer = board.getMember().getProfile().getNickname();
        List<BoardComment> commentList = boardCommentRepository.getCommentList(boardId);
        int commentCnt = commentList.size();
        int likeCnt = board.getBoardInfo().getLikeCnt();
        BoardDetailDto boardDetailDto = new BoardDetailDto(board.getMember().getId(), board.getMovie().getId(), board.getTitle(), board.getContent(), board.getDateTime(), writer, commentList, viewCnt,commentCnt,likeCnt);
        result.put("findBoard", boardDetailDto);

        return result;
    }

    public Long createBoard(BoardCreateDto boardCreateDto) throws IllegalStateException{

        Optional<Member> findMember = memberRepository.findById(boardCreateDto.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(boardCreateDto.getMovieId());

        if(!findMovie.isPresent()) throw new IllegalStateException("존재 하지 않는 영화입니다.");
        if (!findMember.isPresent()) throw new IllegalStateException("존재 하지 않는 멤버입니다.");
        // 게시글 정보 생성
        BoardInfo boardInfo = new BoardInfo();

        Board board = Board.createBoard(boardCreateDto, findMember.get(), findMovie.get(), boardInfo);
        boardRepository.save(board);
        return board.getId();
    }
    @Transactional
    public Long updateBoard(BoardUpdateDto boardUpdateDto) {
        Long boardId = boardUpdateDto.getBoardId();
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if(!findBoard.isPresent()){
            throw new IllegalStateException("수정할 게시글을 찾을 수 없습니다");
        }
        Board board = findBoard.get();
        board.setTitle(boardUpdateDto.getTitle());
        board.setContent(boardUpdateDto.getContent());
        board.setDateTime(LocalDateTime.now());
        return board.getId();
    }

    @Transactional
    public void statusBoard(Long boardId, int statusCode) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if(!findBoard.isPresent()){
            throw new IllegalStateException("존재하지 않는 게시글 없습니다.");
        }
        Board board = findBoard.get();
        // 1:normal 2: banned 3: deleted
        switch (statusCode) {
            case 1:
                board.normalize();
                break;
            case 2:
                board.banned();
                break;
            case 3:
                board.deleted();
                break;
        }
    }
}
