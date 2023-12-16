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
	 * JdbcTemplate batchUpdate() 결과 - 299ms
	 */
	@DisplayName("batchUpdate()를 이용한 평점 수정")
	@Test
	public void batchUpdateTest() {
		// given
		long movieIdFirst = 51608;
		long movieIdSecond = 705996;
		long movieIdThird = 597;
		Movie movieFirst = movieRepository.findById(movieIdFirst).orElseThrow();
		Movie movieSecond = movieRepository.findById(movieIdSecond).orElseThrow();
		Movie movieThird = movieRepository.findById(movieIdThird).orElseThrow();
		List<RatingEntity> ratingEntityList = new ArrayList<>();
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			RatingEntity newRatingFirst = RatingEntity.builder()
				.story(2)
				.acting(2)
				.direction(2)
				.visual(2)
				.sound(2)
				.total(10)
				.movie(movieFirst)
				.member(member)
				.build();
			ratingEntityList.add(newRatingFirst);

			RatingEntity newRatingSecond = RatingEntity.builder()
				.story(2)
				.acting(2)
				.direction(2)
				.visual(2)
				.sound(2)
				.total(10)
				.movie(movieSecond)
				.member(member)
				.build();
			ratingEntityList.add(newRatingSecond);

			RatingEntity newRatingThird = RatingEntity.builder()
				.story(2)
				.acting(2)
				.direction(2)
				.visual(2)
				.sound(2)
				.total(10)
				.movie(movieThird)
				.member(member)
				.build();
			ratingEntityList.add(newRatingThird);
		}

		// when
		ratingCustomRepository.batchUpdate(ratingEntityList);
		long count = ratingRepository.countByMovieIdAndTotalEquals(movieIdFirst, 10L);
		count += ratingRepository.countByMovieIdAndTotalEquals(movieIdSecond, 10L);
		count += ratingRepository.countByMovieIdAndTotalEquals(movieIdThird, 10L);

		// then
		Assertions.assertEquals(memberList.length * 3, count);
	}

	/**
	 * JPA dirtyChecking 결과 - 465ms
	 */
	@DisplayName("dirtyChecking을 이용한 평점 수정")
	@Test
	public void dirtyCheckingTest() {
		// given
		long movieIdFirst = 51608;
		long movieIdSecond = 705996;
		long movieIdThird = 597;
		Movie movieFirst = movieRepository.findById(movieIdFirst).orElseThrow();
		Movie movieSecond = movieRepository.findById(movieIdSecond).orElseThrow();
		Movie movieThird = movieRepository.findById(movieIdThird).orElseThrow();
		List<String> ratingList = new ArrayList<>();
		ratingList.add("3");
		ratingList.add("3");
		ratingList.add("3");
		ratingList.add("3");
		ratingList.add("3");
		ratingList.add("15");

		// when
		for(long memberId : memberList) {
			Member member = memberRepository.findById(memberId).orElseThrow();
			ratingService.updateRating(movieFirst.getId(), member.getId(), ratingList);
			ratingService.updateRating(movieSecond.getId(), member.getId(), ratingList);
			ratingService.updateRating(movieThird.getId(), member.getId(), ratingList);
		}

		long count = ratingRepository.countByMovieIdAndTotalEquals(movieIdFirst, 15L);
		count += ratingRepository.countByMovieIdAndTotalEquals(movieIdSecond, 15L);
		count += ratingRepository.countByMovieIdAndTotalEquals(movieIdThird, 15L);

		// then
		Assertions.assertEquals(memberList.length * 3, count);
	}



}
