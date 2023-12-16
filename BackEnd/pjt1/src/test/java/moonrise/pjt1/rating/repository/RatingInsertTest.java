package moonrise.pjt1.rating.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.rating.entity.RatingEntity;

@SpringBootTest
public class RatingInsertTest {
	@Autowired
	RatingCustomRepository ratingCustomRepository;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	MemberRepository memberRepository;

	static long[] memberList = {2652007073L, 2652473320L, 2655808838L, 2656139612L, 2656229068L, 2663403095L, 2666829744L, 2667958875L, 2668092074L, 2668256507L};
	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * JdbcTemplate batchUpdate() 결과 - 91ms
	 */
	@DisplayName("batchUpdate()를 이용한 평점 등록")
	@Test
	public void batchInsertTest() {
		// given
		long movieId = 51608;
		Movie movie = movieRepository.findById(movieId).orElseThrow();
		List<RatingEntity> ratingEntityList = new ArrayList<>();
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			RatingEntity newRating = RatingEntity.builder()
				.story(1)
				.acting(1)
				.direction(1)
				.visual(1)
				.sound(1)
				.total(5)
				.movie(movie)
				.member(member)
				.build();
			ratingEntityList.add(newRating);
		}

		// when
		ratingCustomRepository.batchInsert(ratingEntityList);
		long count = ratingRepository.countByMovieIdEquals(movieId);

		// then
		Assertions.assertEquals(memberList.length, count);
	}

	/**
	 * JPA saveAll() 결과 - 137ms
	 */
	@DisplayName("saveAll()을 이용한 평점 등록")
	@Test
	public void saveAllTest() {
		// given
		long movieId = 705996;
		Movie movie = movieRepository.findById(movieId).orElseThrow();
		List<RatingEntity> ratingEntityList = new ArrayList<>();
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			RatingEntity newRating = RatingEntity.builder()
				.story(1)
				.acting(1)
				.direction(1)
				.visual(1)
				.sound(1)
				.total(5)
				.movie(movie)
				.member(member)
				.build();
			ratingEntityList.add(newRating);
		}

		// when
		ratingRepository.saveAll(ratingEntityList);
		long count = ratingRepository.countByMovieIdEquals(movieId);

		// then
		Assertions.assertEquals(memberList.length, count);
	}

	/**
	 * JPA save() 결과 - 671ms
	 */
	@DisplayName("save()을 이용한 평점 등록")
	@Test
	public void saveTest() {
		// given
		long movieId = 597;
		Movie movie = movieRepository.findById(movieId).orElseThrow();
		List<RatingEntity> ratingEntityList = new ArrayList<>();
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			RatingEntity newRating = RatingEntity.builder()
				.story(1)
				.acting(1)
				.direction(1)
				.visual(1)
				.sound(1)
				.total(5)
				.movie(movie)
				.member(member)
				.build();
			ratingEntityList.add(newRating);
			ratingRepository.save(newRating);
		}

		// when
		long count = ratingRepository.countByMovieIdEquals(movieId);

		// then
		Assertions.assertEquals(memberList.length, count);
	}

}
