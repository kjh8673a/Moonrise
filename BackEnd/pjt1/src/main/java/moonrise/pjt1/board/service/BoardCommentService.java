package moonrise.pjt1.board.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardCommentCreateDto;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardComment;
import moonrise.pjt1.board.repository.BoardCommentRepository;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public Long createComment(BoardCommentCreateDto boardCommentCreateDto) {
        Optional<Member> findMember = memberRepository.findById(boardCommentCreateDto.getMemberId());
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
        if (boardComment.getGroupId() == 0L){
            boardComment.setGroupId(commentId);
        }
        return boardComment.getId();

    }
}
