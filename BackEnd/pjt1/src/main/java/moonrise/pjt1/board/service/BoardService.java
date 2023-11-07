package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.board.dto.*;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardComment;
import moonrise.pjt1.board.entity.BoardInfo;
import moonrise.pjt1.board.repository.BoardCommentRepository;
import moonrise.pjt1.board.repository.BoardCustomRepository;
import moonrise.pjt1.board.repository.BoardInfoRepository;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.board.request.BoardBookmarkReq;
import moonrise.pjt1.board.request.BoardLikeReq;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.util.HttpUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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
    private final BoardCustomRepository boardCustomRepository;
    private final RedisTemplate redisTemplate;


    public ResponseDto listBoard(Long movieId, Pageable pageable) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Movie> findMovie = movieRepository.findById(movieId);
        Map<String, Object> result = new HashMap<>();
        if (!findMovie.isPresent()) {
            throw new IllegalStateException("존재 하지 않는 영화입니다.");
        }
        Page<Board> boardList = boardRepository.findByMovieId(movieId, pageable);

        List<BoardListResponseDto> findBoards = new ArrayList<>();

        for (Board b : boardList) {
            Long viewCnt = b.getBoardInfo().getViewCnt();
            int commentsCnt = b.getBoardComments().size();
            Long likeCnt = b.getBoardInfo().getLikeCnt();
            String nickname = b.getMember().getProfile().getNickname();
            BoardListResponseDto boardListResponseDto = new BoardListResponseDto(b.getId(), b.getTitle(), b.getContent(), b.getDateTime(), likeCnt, commentsCnt, viewCnt, nickname);
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

    /**
     * 게시글 상세보기
     */
    public ResponseDto detailBoard(Long userId, Long boardId) {
        ResponseDto responseDto = new ResponseDto();

        MemberForDetailProjectionDto member = boardRepository.getMemberForDetail(userId);
        BoardForDetailProjectionDto board = boardCustomRepository.getBoardForDetail(boardId);

        if(board != null) {
            // 조회수
            String keyViewCnt = "boardViewCnt::" + boardId;
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Long viewCnt = board.getViewCnt();
            valueOperations.setIfAbsent(keyViewCnt, String.valueOf(0));
            Long redisViewCnt = Long.parseLong(valueOperations.get(keyViewCnt).toString()) + 1L;
            viewCnt += redisViewCnt;
            valueOperations.set(keyViewCnt, String.valueOf(redisViewCnt));

            // 댓글
            Long commentCnt = board.getCommentCnt();
            List<CommentForDetailProjectionDto> comments = boardRepository.getCommentsForDetail(boardId);

            // 좋아요
            boolean isLike = false;
            if(member != null) {
                isLike = checkLikeStatus(member.getLikeBoard(), member.getUserId(), boardId);
            }
            String keyAdd = "boardLikeAdd::" + boardId;
            String keyDel = "boardLikeDel::" + boardId;
            SetOperations<String, String> setOperations = redisTemplate.opsForSet();
            Long likeCnt = board.getLikeCnt();
            likeCnt += setOperations.size(keyAdd);
            likeCnt -= setOperations.size(keyDel);

            // 북마크
            boolean isBookmark = false;
            if(member != null) {
                isBookmark = checkBookmarkStatus(member.getBookmarkBoard(), member.getUserId(), boardId);
            }

            BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .movieId(board.getMovieId())
                .title(board.getTitle())
                .content(board.getContent())
                .dateTime(board.getDateTime())
                .writer(board.getWriter())
                .boardComments(comments)
                .viewCnt(viewCnt)
                .commentCnt(commentCnt)
                .likeCnt(likeCnt)
                .isLike(isLike)
                .isBookmark(isBookmark)
                .build();

            responseDto.setStatus_code(200);
            responseDto.setMessage("게시글 상세보기 성공.");
            responseDto.setData(boardDetailDto);
        }else {
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }
        
        return responseDto;
    }

    public ResponseDto createBoard(String access_token, BoardCreateDto boardCreateDto) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if (user_id.equals(0L)) {
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
        result.put("boardId", board.getId());
        responseDto.setMessage("게시글 작성 완료");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    @Transactional
    public ResponseDto updateBoard(String access_token, BoardUpdateDto boardUpdateDto) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if (user_id.equals(0L)) {
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        //DB
        Long boardId = boardUpdateDto.getBoardId();
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.get();
        if (user_id.equals(board.getMember().getId())) {
            board.setTitle(boardUpdateDto.getTitle());
            board.setContent(boardUpdateDto.getContent());
            board.setDateTime(LocalDateTime.now());
        } else {
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 게시글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("boardId", board.getId());
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
        if (user_id.equals(0L)) {
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.get();
        if (user_id.equals(board.getMember().getId())) {
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
        } else {
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 게시글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("boardStatus", board.getBoardInfo().getBoardStatus());
        responseDto.setMessage("게시글 상태 변경 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    /**
     * 게시글 좋아요
     */
    public ResponseDto likeBoard(BoardLikeReq boardLikeReq) {
        ResponseDto responseDto = new ResponseDto();

        MemberForLikeProjectionDto member = boardRepository.getMemberForLike(boardLikeReq.getUserId());
        Long board = boardRepository.getBoardId(boardLikeReq.getBoardId());

        if(member != null && board != null) {
            String keyAdd = "boardLikeAdd::" + board;
            String keyDel = "boardLikeDel::" + board;
            SetOperations<String, String> setOperations = redisTemplate.opsForSet();

            // 좋아요를 한 상태라면 좋아요 취소
            if(checkLikeStatus(member.getLikeBoard(), member.getUserId(), board)) {
                // 캐시에 좋아요 등록한 상태면 캐시에서 제거
                if(setOperations.isMember(keyAdd, String.valueOf(member.getUserId()))) {
                    setOperations.remove(keyAdd, String.valueOf(member.getUserId()));
                }
                // 캐시에 좋아요 정보가 없다면 RDB에 있다는 것이므로 좋아요 취소 캐시에 저장
                else {
                    setOperations.add(keyDel, String.valueOf(member.getUserId()));
                }
                responseDto.setMessage("게시물 좋아요 취소.");
            }

            // 좋아요를 안한 상태라면 좋아요 등록
            else {
                // 캐시에 좋아요 취소 상태라면 캐시에서 제거
                if(setOperations.isMember(keyDel, String.valueOf(member.getUserId()))) {
                    setOperations.remove(keyDel, String.valueOf(member.getUserId()));
                }
                // 캐시에 취소 정보가 없다면 좋아요 등록 캐시에 저장
                else {
                    setOperations.add(keyAdd, String.valueOf(member.getUserId()));
                }
                responseDto.setMessage("게시물 좋아요 등록.");
            }
            responseDto.setStatus_code(200);
        }else if(member == null){
            throw new IllegalStateException("존재하지 않는 회원 입니다.");
        }else {
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }

        return responseDto;
    }

    /**
     * 게시글 좋아요 여부 확인
     */
    private boolean checkLikeStatus(String boardLike, Long userId, Long boardId) {
        String keyAdd = "boardLikeAdd::" + boardId;
        String keyDel = "boardLikeDel::" + boardId;
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();

        if(setOperations.isMember(keyAdd, String.valueOf(userId))) {
            return true;
        }else if(setOperations.isMember(keyDel, String.valueOf(userId))) {
            return false;
        }else if(boardLike != null && boardLike.contains(String.valueOf(boardId))) {
            return true;
        }
        return false;
    }

    public ResponseDto bookmarkBoard(BoardBookmarkReq boardBookmarkReq) {
        ResponseDto responseDto = new ResponseDto();

        MemberForBookmarkProjectionDto member = boardRepository.getMemberForBookmark(boardBookmarkReq.getUserId());
        Long board = boardRepository.getBoardId(boardBookmarkReq.getBoardId());

        if(member != null && board != null) {
            String keyAdd = "boardBookmarkAdd::" + board;
            String keyDel = "boardBookmarkDel::" + board;
            SetOperations<String, String> setOperations = redisTemplate.opsForSet();

            if(checkBookmarkStatus(member.getBookmarkBoard(), member.getUserId(), board)) {
                // 캐시에 값이 있다면 있는 값만 없애주면 된다.
                if(setOperations.isMember(keyAdd, String.valueOf(member.getUserId()))) {
                    setOperations.remove(keyAdd, String.valueOf(member.getUserId()));
                }else {
                    setOperations.add(keyDel, String.valueOf(member.getUserId()));
                }
                responseDto.setMessage("게시물 북마크 취소.");
            }
            else {
                if(setOperations.isMember(keyDel, String.valueOf(member.getUserId()))) {
                    setOperations.remove(keyDel, String.valueOf(member.getUserId()));
                }else {
                    setOperations.add(keyAdd, String.valueOf(member.getUserId()));
                }
                responseDto.setMessage("게시물 북마크 등록.");
            }
            responseDto.setStatus_code(200);
        }else if(member == null){
            throw new IllegalStateException("존재하지 않는 회원 입니다.");
        }else {
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }

        return responseDto;
    }

    private boolean checkBookmarkStatus(String boardBookmark, Long userId, Long boardId) {
        String keyAdd = "boardBookmarkAdd::" + boardId;
        String keyDel = "boardBookmarkDel::" + boardId;
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();

        if(setOperations.isMember(keyAdd, String.valueOf(userId))) {
            return true;
        }else if(setOperations.isMember(keyDel, String.valueOf(userId))) {
            return false;
        }else if(boardBookmark != null && boardBookmark.contains(String.valueOf(boardId))) {
            return true;
        }
        return false;
    }

    public ResponseDto bookmarkMypage(String access_token) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        System.out.println("user_id = " + user_id);
        if (user_id.equals(0L)) {
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        // DB
        Optional<Member> findMember = memberRepository.findById(user_id);

        // 먼저 캐시서버에서 찾아보기
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String bookmarkBoardList;
        String bookmarkListKey = "UserBoardBookMarkList::" + user_id;


        if (valueOperations.get(bookmarkListKey) == null) { // 캐시 서버에 값이 없으면
            String bookmarkBoard = findMember.get().getMemberInfo().getBookmarkBoard(); // 디비에서 찾고
            if (bookmarkBoard == null) {   // 북마크 목록이 없는 경우 (한번도 북마크 한 적 없음)
                responseDto.setStatus_code(200);
                responseDto.setData(result);
                responseDto.setMessage("북마크 목록이 없습니다");
                return responseDto;
            } else {  // 북마크 해본적 있음
                String nullstring = "";
                if (bookmarkBoard.equals(nullstring)){ // DB 에서 이미 한번 북마크를 했다가 다시 지워서 null 이 아니지만 빈 값일때
                    responseDto.setStatus_code(200);
                    responseDto.setData(result);
                    responseDto.setMessage("북마크 목록이 없습니다");
                    return responseDto;
                }else bookmarkBoardList = bookmarkBoard;   // DB값 저장

            }

        } else { // 캐시 서버에 값이 있으면
            String nullstring = "";
            bookmarkBoardList = (String) valueOperations.get(bookmarkListKey);
            System.out.println("캐시 서버에 값이 있으면");
            System.out.println("bookmarkBoardList = " + bookmarkBoardList);
            if (bookmarkBoardList.equals(nullstring)){
                responseDto.setStatus_code(200);
                responseDto.setData(result);
                responseDto.setMessage("북마크 목록이 없습니다");
                return responseDto;
            }

        }
        // String 받고 FOR 문 돌면서 boardid 찾고 dto에 정보 저장
        System.out.println("------------------------????????????????????????? 여기 오면 안되는데.. ");
        System.out.println(bookmarkBoardList);
        System.out.println("------------------------??????????????????????????????");
        String[] bookmarks = bookmarkBoardList.split(",");
        List<MypageResponseDto> findBoards = new ArrayList<>();

        for (int i = 0; i < bookmarks.length; i++) {
            Long boardId = Long.parseLong(bookmarks[i]);
            Optional<Board> findBoard = boardRepository.findById(boardId);
            LocalDateTime dateTime = findBoard.get().getDateTime();
            String title = findBoard.get().getTitle();
            String movieTitle = findBoard.get().getMovie().getTitle();
            MypageResponseDto mypageResponseDto = new MypageResponseDto(boardId, dateTime, title, movieTitle);
            findBoards.add(0,mypageResponseDto);
        }
        result.put("findBoards", findBoards);
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        responseDto.setMessage("마이페이지 북마크 리스트 성공 ~");

        return responseDto;
    }

    public ResponseDto boardMypage(String access_token) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        System.out.println("user_id = " + user_id);
        if (user_id.equals(0L)) {
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        // DB

        List<Board> boardList = boardRepository.findByUserId(user_id);

        List<MypageResponseDto> findBoards = new ArrayList<>();

        for (Board b : boardList) {
            Long boardId = b.getId();
            String title = b.getTitle();
            String movieTitle = b.getMovie().getTitle();
            LocalDateTime dateTime = b.getDateTime();
            MypageResponseDto mypageResponseDto = new MypageResponseDto(boardId, dateTime, title, movieTitle);
            findBoards.add(mypageResponseDto);
        }
        result.put("findBoards", findBoards);
        //responseDto 작성
        responseDto.setMessage("내가 쓴 게시글 목록 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }


}
