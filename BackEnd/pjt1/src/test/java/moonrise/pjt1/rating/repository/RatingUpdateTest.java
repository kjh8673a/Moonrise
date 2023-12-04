package moonrise.pjt1.rating.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.rating.entity.RatingEntity;
import moonrise.pjt1.rating.service.RatingService;

@SpringBootTest
public class RatingUpdateTest {
	@Autowired
	RatingCustomRepository ratingCustomRepository;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	RatingService ratingService;

	static long[] memberList = {2652007073L, 2652473320L, 2655808838L, 2656139612L, 2656229068L, 2663403095L, 2666829744L, 2667958875L, 2668092074L, 2668256507L};
	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * JdbcTemplate batchUpdate() 결과 - 292ms
	 */
	@DisplayName("평점 수정 batchUpdate")
	@Test
	public void batchUpdateTest() {
		// given
		long movieIdFirst = 51608;
		long movieIdSecond = 705996;
		Movie movieFirst = movieRepository.findById(movieIdFirst).orElseThrow();
		Movie movieSecond = movieRepository.findById(movieIdSecond).orElseThrow();
		List<RatingEntity> ratingEntityList = new ArrayList<>();
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			RatingEntity newRatingFirst = RatingEntity.builder()
				.story(1)
				.acting(2)
				.direction(3)
				.visual(4)
				.sound(5)
				.total(15)
				.movie(movieFirst)
				.member(member)
				.build();
			ratingEntityList.add(newRatingFirst);

			RatingEntity newRatingSecond = RatingEntity.builder()
				.story(1)
				.acting(2)
				.direction(3)
				.visual(4)
				.sound(5)
				.total(15)
				.movie(movieSecond)
				.member(member)
				.build();
			ratingEntityList.add(newRatingSecond);
		}

		// when
		ratingCustomRepository.batchUpdate(ratingEntityList);
		long count = ratingRepository.countByMovieIdAndTotalEquals(movieIdFirst, 15L);
		count += ratingRepository.countByMovieIdAndTotalEquals(movieIdSecond, 15L);

		// then
		Assertions.assertEquals(memberList.length * 2, count);
	}

	/**
	 * JPA dirtyChecking 결과 - 457ms
	 */
	@DisplayName("평점 수정 dirtyChecking")
	@Test
	public void dirtyCheckingTest() {
		// given
		long movieIdFirst = 51608;
		long movieIdSecond = 705996;
		Movie movieFirst = movieRepository.findById(movieIdFirst).orElseThrow();
		Movie movieSecond = movieRepository.findById(movieIdSecond).orElseThrow();

		List<String> ratingList = new ArrayList<>();
		ratingList.add("2");
		ratingList.add("3");
		ratingList.add("4");
		ratingList.add("5");
		ratingList.add("6");
		ratingList.add("20");

		// when
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			ratingService.updateRating(movieIdFirst, memberId, ratingList);
			ratingService.updateRating(movieIdSecond, memberId, ratingList);
		}

		long count = ratingRepository.countByMovieIdAndTotalEquals(movieIdFirst, 20L);
		count += ratingRepository.countByMovieIdAndTotalEquals(movieIdSecond, 20L);
		// then
		Assertions.assertEquals(memberList.length * 2, count);
	}



}
