package moonrise.pjt1.rating.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import moonrise.pjt1.rating.entity.RatingEntity;

import static moonrise.pjt1.rating.entity.QRatingEntity.*;

@Repository
public class RatingCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	public RatingCustomRepository(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
		this.queryFactory = new JPAQueryFactory(entityManager);
		this.entityManager = entityManager;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<RatingEntity> getRatingNotInList(Long movieId, List<Long> list) {
		return queryFactory
			.selectFrom(ratingEntity)
			.where(ratingEntity.movie.id.eq(movieId), ratingEntity.member.id.notIn(list))
			.fetch();
	}

	public void batchInsert(List<RatingEntity> ratingList) {
		String sql = "INSERT INTO rating (movie_id, member_id, acting, direction, sound, story, visual, total) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				RatingEntity rating = ratingList.get(i);

				ps.setLong(1, rating.getMovie().getId());
				ps.setLong(2, rating.getMember().getId());
				ps.setLong(3, rating.getActing());
				ps.setLong(4, rating.getDirection());
				ps.setLong(5, rating.getSound());
				ps.setLong(6, rating.getStory());
				ps.setLong(7, rating.getVisual());
				ps.setLong(8, rating.getTotal());
			}

			@Override
			public int getBatchSize() {
				return ratingList.size();
			}
		});
	}

	public void batchUpdate(List<RatingEntity> ratingList) {
		String sql = "UPDATE rating SET acting = ?, direction = ?, sound = ?, story = ?, visual = ?, total = ? "
			+ "WHERE movie_id = ? AND member_id = ?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				RatingEntity rating = ratingList.get(i);

				ps.setLong(1, rating.getActing());
				ps.setLong(2, rating.getDirection());
				ps.setLong(3, rating.getSound());
				ps.setLong(4, rating.getStory());
				ps.setLong(5, rating.getVisual());
				ps.setLong(6, rating.getTotal());

				ps.setLong(7, rating.getMovie().getId());
				ps.setLong(8, rating.getMember().getId());
			}

			@Override
			public int getBatchSize() {
				return ratingList.size();
			}
		});
	}
}
