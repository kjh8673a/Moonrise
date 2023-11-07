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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BoardCustomRepository {
	private final JPAQueryFactory queryFactory;

	@PersistenceContext
	private EntityManager entityManager;

	public BoardCustomRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
		this.entityManager = entityManager;
	}

	public BoardForDetailProjectionDto getBoardForDetail(Long boardId) {
		return queryFactory
			.select(Projections.constructor(BoardForDetailProjectionDto.class,
				board.id.as("boardId"),
				board.movie.id.as("movieId"),
				board.title,
				board.content,
				board.dateTime,
				member.profile.nickname.as("writer"),
				boardInfo.viewCnt.coalesce(0L).as("viewCnt"),
				boardComment.id.count().coalesce(0L).as("commentCnt"),
				boardInfo.likeCnt.coalesce(0L).as("likeCnt")
				))
			.from(board)
			.join(board.member, member)
			.leftJoin(board.boardComments, boardComment)
			.leftJoin(board.boardInfo, boardInfo)
			.where(board.id.eq(boardId))
			.fetchOne();
	}

}
