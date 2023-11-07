package moonrise.pjt1.board.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.board.dto.BoardForDetailProjectionDto;

import static moonrise.pjt1.board.entity.QBoard.*;
import static moonrise.pjt1.board.entity.QBoardComment.*;
import static moonrise.pjt1.board.entity.QBoardInfo.*;
import static moonrise.pjt1.member.entity.QMember.*;

@RequiredArgsConstructor
@Repository
public class BoardCustomRepository {
	private final JPAQueryFactory queryFactory;

	public BoardForDetailProjectionDto getBoardForDetailProjectionDto(Long boardId) {
		return queryFactory
			.select(Projections.constructor(BoardForDetailProjectionDto.class,
				board.id.as("boardId"),
				board.movie.id.as("movieId"),
				board.title,
				board.content,
				board.dateTime,
				member.profile.nickname.as("writer"),
				boardInfo.viewCnt,
				boardComment.id.count().as("commentCnt"),
				boardInfo.likeCnt
				))
			.from(board)
			.join(board.member, member)
			.leftJoin(board.boardComments, boardComment)
			.leftJoin(board.boardInfo, boardInfo)
			.where(board.id.eq(boardId))
			.fetchOne();
	}

}
