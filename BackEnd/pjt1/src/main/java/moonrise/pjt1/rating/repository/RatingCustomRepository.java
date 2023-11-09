package moonrise.pjt1.rating.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import moonrise.pjt1.rating.entity.RatingEntity;

import static moonrise.pjt1.rating.entity.QRatingEntity.*;

@Repository
public class RatingCustomRepository {
	private final JPAQueryFactory queryFactory;

	@PersistenceContext
	private EntityManager entityManager;

	public RatingCustomRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
		this.entityManager = entityManager;
	}

	public List<RatingEntity> getRatingNotInList(Long movieId, List<Long> list) {
		return queryFactory
			.selectFrom(ratingEntity)
			.where(ratingEntity.movie.id.eq(movieId), ratingEntity.member.id.notIn(list))
			.fetch();
	}

}
