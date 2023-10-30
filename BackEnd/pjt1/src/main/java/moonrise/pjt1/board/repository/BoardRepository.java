package moonrise.pjt1.board.repository;

import moonrise.pjt1.board.dto.BoardForDetailProjectionDto;
import moonrise.pjt1.board.dto.CommentForDetailProjectionDto;
import moonrise.pjt1.board.dto.MemberForBookmarkProjectionDto;
import moonrise.pjt1.board.dto.MemberForDetailProjectionDto;
import moonrise.pjt1.board.dto.MemberForLikeProjectionDto;
import moonrise.pjt1.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board,Long> {
//    @Query("select new moonrise.pjt1.board.dto.BoardListResponseDto" +"(b.id,b.title,b.content,b.dateTime, b.member.profile.nickname) from Board as b where b.movie.id =:movieId ")
    @Query("select b from Board b where b.movie.id =:movieId and b.boardInfo.boardStatus ='NORMAL'")
    Page<Board> findByMovieId(@Param("movieId") Long movieId, Pageable pageable);

    @Query("select b from Board b where b.member.id =:userId order by b.id desc")
    List<Board> findByUserId(@Param("userId") Long userId);

    @Query(value = "select member.member_id as userId, member_info.like_board as likeBoard "
        + "from member left join member_info on member.member_info_id = member_info.member_info_id "
        + "where member.member_id = :userId"
        , nativeQuery = true)
    MemberForLikeProjectionDto getMemberForLike(@Param("userId") Long userId);

    @Query("select b.id from Board b where b.id = :boardId and b.boardInfo.boardStatus ='NORMAL'")
    Long getBoardId(@Param("boardId") Long boardId);

    @Query(value = "select b.board_id as boardId, b.movie_id as movieId, b.title, b.content, b.write_date as dateTime, p.nickname as writer, i.view_cnt, ifnull(c.comment_cnt, 0) as commentCnt, i.like_cnt "
        + "from board as b "
        + "join (select member.member_id, profile.nickname from member join profile on member.profile_id = profile.profile_id) as p "
        + "on b.member_id = p.member_id "
        + "left join (select count(comment_id) as comment_cnt, board_id from board_comment group by board_id) as c "
        + "on b.board_id = c.board_id "
        + "left join board_info as i "
        + "on b.board_info_id = i.board_info_id "
        + "where b.board_id = :boardId"
        , nativeQuery = true)
    BoardForDetailProjectionDto getBoardForDetail(@Param("boardId") Long boardId);

    @Query(value = "select comment_id as commentId, member_id as memberId, content, writer, write_date as dateTime"
        + "from board_comment"
        + "where board_id = :boardId "
        + "and board_comment_status = 'NORMAL'"
        , nativeQuery = true)
    List<CommentForDetailProjectionDto> getCommentsForDetail(@Param("boardId") Long boardId);

    @Query(value = "select member.member_id as userId, member_info.bookmark_board as bookmarkBoard "
        + "from member left join member_info on member.member_info_id = member_info.member_info_id "
        + "where member.member_id = :userId"
        , nativeQuery = true)
    MemberForBookmarkProjectionDto getMemberForBookmark(@Param("userId") Long userId);

    @Query(value = "select member.member_id as userId, member_info.like_board as likeBoard, member_info.bookmark_board as bookmarkBoard "
        + "from member left join member_info on member.member_info_id = member_info.member_info_id "
        + "where member.member_id = :userId"
        , nativeQuery = true)
    MemberForDetailProjectionDto getMemberForDetail(@Param("userId") Long userId);
}
