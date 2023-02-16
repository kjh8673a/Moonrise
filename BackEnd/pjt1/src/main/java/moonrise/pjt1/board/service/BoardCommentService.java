package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.board.dto.BoardCommentUpdateDto;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardComment;
import moonrise.pjt1.board.repository.BoardCommentRepository;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.util.HttpUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public ResponseDto createComment(String access_token,BoardCommentCreateDto boardCommentCreateDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        Long user_id = HttpUtil.requestParingToken(access_token);

        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Member> findMember = memberRepository.findById(user_id);
        Optional<Board> findBoard = boardRepository.findById(boardCommentCreateDto.getBoardId());
        if(!findBoard.isPresent()){
            throw new IllegalStateException("게시글을 찾을 수 없습니다");
        }
        if(!findBoard.isPresent()){
            throw new IllegalStateException("회원을 찾을 수 없습니다");
        }

        BoardComment boardComment = BoardComment.createBoardComment(boardCommentCreateDto, findBoard.get(), findMember.get());
        boardCommentRepository.save(boardComment);
        Long commentId = boardComment.getId();
        // 원댓글이면 groupId 본인 pk로 저장
        if (boardComment.getGroupId().equals(0L)){
            boardComment.setGroupId(commentId);
        }
        //responseDto 작성
        result.put("boardCommentId",commentId);
        responseDto.setMessage("댓글 작성 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;

    }
    @Transactional
    public ResponseDto updateComment(String access_token,BoardCommentUpdateDto boardCommentUpdateDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Long commentId = boardCommentUpdateDto.getCommentId();
        Optional<BoardComment> findComment = boardCommentRepository.findById(commentId);
        if(!findComment.isPresent()){
            throw new IllegalStateException("수정할 댓글을 찾을 수 없습니다.");
        }
        BoardComment boardComment = findComment.get();
        if(user_id.equals(boardComment.getMember().getId())) {
            boardComment.setContent(boardCommentUpdateDto.getContent());
            boardComment.setWriteDate(LocalDateTime.now());
        }
        else{
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 댓글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("boardCommentId",boardComment.getId());
        responseDto.setMessage("소모임 댓글수정 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    @Transactional
    public ResponseDto statusComment(String access_token,Long commentId, int statusCode) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<BoardComment> findComment = boardCommentRepository.findById(commentId);
        if(!findComment.isPresent()){
            throw new IllegalStateException("존재하지 않는 댓글입니다.");
        }
        BoardComment boardComment = findComment.get();
        if(user_id.equals(boardComment.getMember().getId())) {
            // 1:normal 2: banned 3: deleted
            switch (statusCode) {
                case 1:
                    boardComment.normalize();
                    break;
                case 2:
                    boardComment.banned();
                    break;
                case 3:
                    boardComment.deleted();
                    break;
            }
        }
        else{
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 댓글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("boardCommentId",boardComment.getId());
        responseDto.setMessage("소모임 댓글상태 변경 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
}
