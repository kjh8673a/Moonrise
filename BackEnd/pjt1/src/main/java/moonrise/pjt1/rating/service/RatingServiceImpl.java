package moonrise.pjt1.rating.service;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.entity.RatingEntity;
import moonrise.pjt1.rating.repository.RatingCustomRepository;
import moonrise.pjt1.rating.repository.RatingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class RatingServiceImpl implements RatingService {

	private final RatingRepository ratingRepository;
	private final MovieRepository movieRepository;
	private final MemberRepository memberRepository;
	private final RedisTemplate redisTemplate;
	private final RatingCustomRepository ratingCustomRepository;

	@Autowired
	public RatingServiceImpl(RatingRepository ratingRepository, MovieRepository movieRepository,
		MemberRepository memberRepository, RedisTemplate redisTemplate, RatingCustomRepository ratingCustomRepository) {
		this.ratingRepository = ratingRepository;
		this.movieRepository = movieRepository;
		this.memberRepository = memberRepository;
		this.redisTemplate = redisTemplate;
		this.ratingCustomRepository = ratingCustomRepository;
	}

	@Override
	public RatingEntity createRating(RatingDto dto) {
		Movie movie = movieRepository.findById(dto.getMovieId())
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 영화 입니다."));
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 회원 입니다."));

		ListOperations listOperations = redisTemplate.opsForList();
		String totalKey = "ratingTotal::" + movie.getId();
		if (!redisTemplate.hasKey(totalKey)) {
			createTotalRatingCache(movie.getId());
		}

		String personalKey = "ratingPersonal::" + movie.getId() + "::" + member.getId();
		if (!redisTemplate.hasKey(personalKey)) {
			createPersonalRatingCache(movie.getId(), member.getId());
		}

		String key = "ratingAdd::" + movie.getId() + "::" + member.getId();
		if (redisTemplate.hasKey(key)) {
			updateRatingCache(key, totalKey, dto);
		} else {
			createRatingCache(key, totalKey, dto);
		}

		return new RatingEntity();
	}

    private void updatePersonalRatingCache(RatingDto dto) {
        String key = "ratingPersonal::" + dto.getMovieId() + "::" + dto.getMemberId();

        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.set(key, 0, dto.getStory());
        listOperations.set(key, 1, dto.getActing());
        listOperations.set(key, 2, dto.getDirection());
        listOperations.set(key, 3, dto.getVisual());
        listOperations.set(key, 4, dto.getSound());
        listOperations.set(key, 5, dto.getTotal());

		listOperations.getOperations().expire(key, Duration.ofMinutes(5));
    }

	private void createPersonalRatingCache(Long movieId, Long memberId) {
		RatingEntity myRatingFromDB = ratingRepository.findPersonal(movieId, memberId);

		ListOperations listOperations = redisTemplate.opsForList();
		String key = "ratingPersonal::" + movieId + "::" + memberId;
		if (myRatingFromDB != null) {
			listOperations.rightPush(key, myRatingFromDB.getStory());
			listOperations.rightPush(key, myRatingFromDB.getActing());
			listOperations.rightPush(key, myRatingFromDB.getDirection());
			listOperations.rightPush(key, myRatingFromDB.getVisual());
			listOperations.rightPush(key, myRatingFromDB.getSound());
			listOperations.rightPush(key, myRatingFromDB.getTotal());
		} else {
			listOperations.rightPushAll(key, String.valueOf(0), String.valueOf(0), String.valueOf(0), String.valueOf(0),
				String.valueOf(0), String.valueOf(0));
		}
        listOperations.getOperations().expire(key, Duration.ofMinutes(5));
	}

	private void createTotalRatingCache(long movieId) {
		Long[] result = new Long[7];

		Set<String> redisRatingKeys = redisTemplate.keys("ratingAdd::" + String.valueOf(movieId));
		List<Long> memberList = new ArrayList<>();
		redisRatingKeys.forEach(data -> {
			memberList.add(Long.parseLong(data.split("::")[2]));

			List<String> ratingFromRedis = redisTemplate.opsForList().range(data, 0, 5);
			for (int i = 0; i < 6; i++) {
				result[i] += Long.parseLong(ratingFromRedis.get(5 - i));
			}
		});
		result[6] += redisRatingKeys.size();

		List<RatingEntity> dbList = ratingCustomRepository.getRatingNotInList(movieId, memberList);
		dbList.forEach(data -> {
			result[0] += data.getTotal();
			result[1] += data.getSound();
			result[2] += data.getVisual();
			result[3] += data.getDirection();
			result[4] += data.getActing();
			result[5] += data.getStory();
		});
		result[6] += dbList.size();

		String totalKey = "ratingTotal::" + movieId;
		ListOperations listOperations = redisTemplate.opsForList();
		for (int i = 6; i >= 0; i--) {
			listOperations.leftPush(totalKey, String.valueOf(result[i]));
		}
		listOperations.getOperations().expire(totalKey, Duration.ofMinutes(5));
	}

	private void updateRatingCache(String key, String totalKey, RatingDto dto) {
		ListOperations listOperations = redisTemplate.opsForList();
		List<String> myRating = listOperations.range(key, 0, 5);
		List<String> totalRating = listOperations.range(totalKey, 0, 6);
		for (int i = 0; i < 6; i++) {
			listOperations.set(totalKey, i, calcNewRating(myRating, totalRating, dto, i));
		}
		listOperations.getOperations().expire(totalKey, Duration.ofMinutes(5));

		listOperations.set(key, 0, dto.getStory());
		listOperations.set(key, 1, dto.getActing());
		listOperations.set(key, 2, dto.getDirection());
		listOperations.set(key, 3, dto.getVisual());
		listOperations.set(key, 4, dto.getSound());
		listOperations.set(key, 5, dto.getTotal());

        updatePersonalRatingCache(dto);
	}

    private void createRatingCache(String key, String totalKey, RatingDto dto) {
		ListOperations listOperations = redisTemplate.opsForList();
		List<String> totalRating = listOperations.range(totalKey, 0, 6);

		RatingEntity myRatingFromDB = ratingRepository.findPersonal(dto.getMovieId(), dto.getMemberId());
		List<String> myRating = new ArrayList<>();
		if (myRatingFromDB != null) {
			myRating.add(String.valueOf(myRatingFromDB.getStory()));
			myRating.add(String.valueOf(myRatingFromDB.getActing()));
			myRating.add(String.valueOf(myRatingFromDB.getDirection()));
			myRating.add(String.valueOf(myRatingFromDB.getVisual()));
			myRating.add(String.valueOf(myRatingFromDB.getSound()));
			myRating.add(String.valueOf(myRatingFromDB.getTotal()));
		} else {
			for (int i = 0; i < 6; i++) {
				myRating.add(String.valueOf(0));
			}
			listOperations.set(totalKey, 6, String.valueOf(Long.valueOf(totalRating.get(6).toString()) + 1));
		}

		for (int i = 0; i < 6; i++) {
			listOperations.set(totalKey, i, calcNewRating(myRating, totalRating, dto, i));
		}
		listOperations.getOperations().expire(totalKey, Duration.ofMinutes(5));

		listOperations.rightPush(key, dto.getStory());
		listOperations.rightPush(key, dto.getActing());
		listOperations.rightPush(key, dto.getDirection());
		listOperations.rightPush(key, dto.getVisual());
		listOperations.rightPush(key, dto.getSound());
		listOperations.rightPush(key, dto.getTotal());

        updatePersonalRatingCache(dto);
	}

	private String calcNewRating(List<String> myRating, List<String> totalRating, RatingDto dto, int i) {
		Long total = Long.parseLong(totalRating.get(i).toString());
		Long prev = Long.parseLong(myRating.get(i).toString());
		Long change = 0L;
		switch (i) {
			case 0:
				change = dto.getStory();
				break;
			case 1:
				change = dto.getActing();
				break;
			case 2:
				change = dto.getDirection();
				break;
			case 3:
				change = dto.getVisual();
				break;
			case 4:
				change = dto.getSound();
				break;
			case 5:
				change = dto.getTotal();
				break;
		}

		return String.valueOf(total - prev + change);
	}

	/**
	 * 평점입력
	 */
	@Override
	public RatingEntity createRating(long movieId, long memberId, RatingDto dto) {
		Optional<Movie> movie = movieRepository.findById(movieId);
		Optional<Member> member = memberRepository.findById(memberId);
		//DB에 저장
		RatingEntity ratingEntity = RatingEntity.builder()
			.acting(dto.getActing())
			.direction(dto.getDirection())
			.sound(dto.getSound())
			.story(dto.getStory())
			.visual(dto.getVisual())
			.total(dto.getTotal())
			.movie(movie.get())
			.member(member.get())
			.build();
		ratingRepository.save(ratingEntity);
		String key = "rating::" + movieId;
		ListOperations listOperations = redisTemplate.opsForList();
		if (listOperations.size(key) == 0) {
			createToCache(movieId);
		} else {
			addToCache(key, dto);
		}
		return ratingEntity;
	}

	/**
	 * DB update
	 */
	@Override
	public long[] updateRating(long ratingId, RatingDto dto) {
		long sum = dto.getActing() + dto.getDirection() + dto.getSound() + dto.getStory() + dto.getVisual();
		Optional<RatingEntity> rating = ratingRepository.findById(ratingId);
		RatingEntity ratingEntity = rating.get();
		ratingEntity.setActing(dto.getActing());
		ratingEntity.setDirection(dto.getDirection());
		ratingEntity.setSound(dto.getSound());
		ratingEntity.setStory(dto.getStory());
		ratingEntity.setVisual(dto.getVisual());
		ratingEntity.setTotal(sum);
		ratingRepository.save(ratingEntity);
		dto.setTotal(sum);
		String key = "rating::" + dto.getMovieId();
		redisTemplate.delete(key);
		return createToCache(dto.getMovieId());
	}

	/**
	 * 캐시에서 찾기
	 */
	@Override
	public List<Long> findRating(long movieId) {
		try {
			List<RatingEntity> db = ratingRepository.findRatingList(movieId);
			String key = "rating::" + movieId;
			ListOperations listOperations = redisTemplate.opsForList();
			if (redisTemplate.hasKey(key)) { //캐시에 값있으면
				Object[] obj = listOperations.range(key, 0, 6).toArray();
				List<Long> result = new ArrayList<>();
				for (int i = 0; i < 7; i++) {
					result.add(Long.parseLong(obj[i].toString()));
				}
				return result;
			} else { //없으면 만들기
				long[] arr = createToCache(movieId);
				List<Long> result = new ArrayList<>();
				for (int i = 0; i < 7; i++) {
					result.add(arr[i]);
				}
				return result;
			}

		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * 개인별 평점조회
	 */
	@Override
	public List<Long> findPersonal(long movieId, long memberId) {
		try {
			RatingEntity db = ratingRepository.findPersonal(movieId, memberId);
			List<Long> result = new ArrayList<>();
			result.add(db.getId());
			result.add(db.getStory());
			result.add(db.getActing());
			result.add(db.getDirection());
			result.add(db.getVisual());
			result.add(db.getSound());
			return result;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * 캐시에 추가
	 */
	@Override
	public long[] createToCache(long movieId) {
		long[] result = new long[7];
		List<RatingEntity> db = ratingRepository.findRatingList(movieId);
		for (int i = 0; i < db.size(); i++) {
			result[0] += db.get(i).getTotal();
			result[1] += db.get(i).getStory();
			result[2] += db.get(i).getActing();
			result[3] += db.get(i).getDirection();
			result[4] += db.get(i).getVisual();
			result[5] += db.get(i).getSound();
		}
		result[6] = ratingRepository.countByMovieIdEquals(movieId); //개수
		String key = "rating::" + movieId;
		ListOperations listOperations = redisTemplate.opsForList();
		listOperations.leftPush(key, String.valueOf(result[0]));
		listOperations.leftPush(key, String.valueOf(result[1]));
		listOperations.leftPush(key, String.valueOf(result[2]));
		listOperations.leftPush(key, String.valueOf(result[3]));
		listOperations.leftPush(key, String.valueOf(result[4]));
		listOperations.leftPush(key, String.valueOf(result[5]));
		listOperations.leftPush(key, String.valueOf(result[6]));
		return result;
	}

	/**
	 * 캐시에서 수정
	 */
	@Override
	public List<Long> addToCache(String key, RatingDto dto) {
		ListOperations listOperations = redisTemplate.opsForList();
		List<Long> result = new ArrayList<>();
		for (int n = 0; n < 7; n++) {
			String str = listOperations.rightPop(key).toString();
			Long num = Long.parseLong(str);
			result.add(num);
		}
		listOperations.leftPush(key, String.valueOf(result.get(0) + dto.getTotal()));
		listOperations.leftPush(key, String.valueOf(result.get(1) + dto.getStory()));
		listOperations.leftPush(key, String.valueOf(result.get(2) + dto.getActing()));
		listOperations.leftPush(key, String.valueOf(result.get(3) + dto.getDirection()));
		listOperations.leftPush(key, String.valueOf(result.get(4) + dto.getVisual()));
		listOperations.leftPush(key, String.valueOf(result.get(5) + dto.getSound()));
		listOperations.leftPush(key, String.valueOf(result.get(6) + 1));
		return result;
	}
}
