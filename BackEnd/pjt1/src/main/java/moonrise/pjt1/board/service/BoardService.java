package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.board.dto.*;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardComment;
import moonrise.pjt1.board.entity.BoardInfo;
import moonrise.pjt1.board.repository.BoardCommentRepository;
import moonrise.pjt1.board.repository.BoardInfoRepository;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.util.HttpUtil;
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
@Log4j2
public class BoardService {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final BoardInfoRepository boardInfoRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final RedisTemplate redisTemplate;


    public ResponseDto listBoard(Long movieId, Pageable pageable){
        ResponseDto responseDto = new ResponseDto();
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
        //responseDto 작성
        responseDto.setMessage("게시글 목록 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    public ResponseDto detailBoard(String access_token,Long boardId){

        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Optional<Member> findMember = memberRepository.findById(user_id);
        String likeBoard = findMember.get().getMemberInfo().getLikeBoard();
        boolean isLike = likeBoard.contains(boardId + ",");
        //***************redis 캐시서버**********************
        String key = "boardViewCnt::"+boardId;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Long boardInfoId = findBoard.get().getBoardInfo().getId();
        if(valueOperations.get(key)==null){
            valueOperations.set(key, String.valueOf(boardInfoRepository.findBoardViewCnt(boardInfoId)+1),20, TimeUnit.MINUTES);
        }else {
            valueOperations.increment(key);
        }
        int viewCnt = Integer.parseInt((String) valueOperations.get(key));
        //***************redis 캐시서버**********************
        //***************DB 조회**********************
        if(!findBoard.isPresent()) throw new IllegalStateException("존재하지 않는 게시글 입니다");
        Board board = findBoard.get();
        String writer = board.getMember().getProfile().getNickname();
        List<BoardComment> commentList = boardCommentRepository.getCommentList(boardId);
        int commentCnt = commentList.size();
        int likeCnt = board.getBoardInfo().getLikeCnt();
        BoardDetailDto boardDetailDto = new BoardDetailDto(board.getMovie().getId(), board.getTitle(), board.getContent(), board.getDateTime(), writer, commentList, viewCnt,commentCnt,likeCnt);

        //responseDto 작성
        if(user_id.equals(board.getMember().getId())){
            result.put("isWriter",true);
        }
        else result.put("isWriter",false);
        result.put("findBoard", boardDetailDto);
        result.put("isLike", isLike);
        responseDto.setMessage("게시글 상세보기 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    public ResponseDto createBoard(String access_token,BoardCreateDto boardCreateDto) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        //DB
        Optional<Member> findMember = memberRepository.findById(user_id);
        Optional<Movie> findMovie = movieRepository.findById(boardCreateDto.getMovieId());
        BoardInfo boardInfo = new BoardInfo();

        Board board = Board.createBoard(boardCreateDto, findMember.get(), findMovie.get(), boardInfo);
        boardRepository.save(board);
        //responseDto 작성
        result.put("boardId",board.getId());
        responseDto.setMessage("게시글 작성 완료");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    @Transactional
    public ResponseDto updateBoard(String access_token,BoardUpdateDto boardUpdateDto) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        //DB
        Long boardId = boardUpdateDto.getBoardId();
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.get();
        if(user_id.equals(board.getMember().getId())) {
            board.setTitle(boardUpdateDto.getTitle());
            board.setContent(boardUpdateDto.getContent());
            board.setDateTime(LocalDateTime.now());
        }
        else{
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 게시글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("boardId",board.getId());
        responseDto.setMessage("게시글 수정 완료");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    @Transactional
    public ResponseDto statusBoard(String access_token, Long boardId, int statusCode) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.get();
        if(user_id.equals(board.getMember().getId())) {
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
        else{
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 게시글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("boardStatus",board.getBoardInfo().getBoardStatus());
        responseDto.setMessage("게시글 상태 변경 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

@Transactional
public ResponseDto likeBoard(String access_token, BoardLikeDto boardLikeDto) {
    Map<String, Object> result = new HashMap<>();
    ResponseDto responseDto = new ResponseDto();

    // token parsing 요청
    Long user_id = HttpUtil.requestParingToken(access_token);
    if(user_id.equals(0L)){
        responseDto.setStatus_code(400);
        responseDto.setMessage("회원 정보가 없습니다.");
        return responseDto;
    }
    // DB
    Long boardId = boardLikeDto.getBoardId();
    Optional<Member> findMember = memberRepository.findById(user_id);
    Optional<Board> findBoard = boardRepository.findById(boardId);
    int status = boardLikeDto.getStatus();
    String cntKey = "boardLikeCnt::"+boardId;
    String listKey = "UserBoardLikeList::"+ user_id;
    ValueOperations valueOperations = redisTemplate.opsForValue();

    // 좋아요 -> LIKECNT ++, LIKEBOARD 에 boardid 추가
    if(status ==1 ){
        // cnt 캐시
        if(valueOperations.get(cntKey)==null){ // 캐시에 값이 없을 경우 레포지토리에서 조회 후 저장
            valueOperations.set(cntKey, String.valueOf(findBoard.get().getBoardInfo().getLikeCnt()+1),20,TimeUnit.MINUTES);
        }else{ // 캐시에 값이 있는 경우
            valueOperations.increment(cntKey);
        }
        // list 캐시
        if(valueOperations.get(listKey)==null){ // 캐시에 값없는 경우 레포지토리에서 조회 후 저장
            String s = findMember.get().getMemberInfo().getLikeBoard() +boardId +",";
            valueOperations.set(listKey, s);

        }else { // 캐시에서 값 가져온 다음 변경 후 저장
            String s =  valueOperations.get(listKey) + String.valueOf(boardId)+",";
            valueOperations.set(listKey, s,20, TimeUnit.MINUTES);

        }

    }else{ // 좋아요취소

        // cnt 캐시
        if(valueOperations.get(cntKey)==null){ // 캐시에 값이 없을 경우 레포지토리에서 조회 후 저장
            valueOperations.set(cntKey, String.valueOf(findBoard.get().getBoardInfo().getLikeCnt()-1),20,TimeUnit.MINUTES);

        }else { // 캐시에 값이 있는 경우
            valueOperations.decrement(cntKey);
        }
        // list 캐시
        if(valueOperations.get(listKey)==null){ // 캐시에 값없는 경우 레포지토리에서 조회 후 저장
            String s = findMember.get().getMemberInfo().getLikeBoard();
            StringBuilder sb = new StringBuilder(s);
            String boardIdString = boardId+",";
            int boardIdIndex = sb.indexOf(boardIdString);
            System.out.println("boardIdIndex = " + boardIdIndex);
            int boardIdStringLen = boardIdString.length();
            sb.delete(boardIdIndex, boardIdIndex+boardIdStringLen);
            valueOperations.set(listKey, sb.toString(),20,TimeUnit.MINUTES);
        }else {
            System.out.println("캐시에 값있음   ----------------------------------------");
            String s = (String) valueOperations.get(listKey);
            StringBuilder sb = new StringBuilder(s);
            String boardIdString = boardId+",";
            int boardIdIndex = sb.indexOf(boardIdString);
            int boardIdStringLen = boardIdString.length();
            sb.delete(boardIdIndex, boardIdIndex+boardIdStringLen);
            valueOperations.set(listKey, sb.toString(),20,TimeUnit.MINUTES);

        }
    }
    int likeCnt = Integer.parseInt((String) valueOperations.get(cntKey));
    String likeList  = (String) valueOperations.get(listKey);
    result.put("boardId", boardId);
    result.put("likeCnt", likeCnt);
    result.put("likeList", likeList);
    responseDto.setData(result);
    responseDto.setMessage("게시글 좋아요 성공");
    responseDto.setStatus_code(200);
    return responseDto;
}
}
