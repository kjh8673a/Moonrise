package moonrise.pjt1.rating.service;

import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.dto.RatingResponseDto;
import moonrise.pjt1.rating.entity.RatingEntity;
import moonrise.pjt1.rating.repository.RatingCustomRepository;
import moonrise.pjt1.rating.repository.RatingRepository;
import moonrise.pjt1.rating.request.RatingCreateReq;

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

	/**
	 * 평점 등록
	 */
	@Override
	public ResponseDto createRating(RatingCreateReq dto) {
		Movie movie = movieRepository.findById(dto.getMovieId())
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 영화 입니다."));
		Member member = memberRepository.findById(dto.getMemberId())
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 회원 입니다."));

		// 해당 영화의 조회용 전체 평점이 캐시에 없다면 캐시 생성
		String totalKey = "ratingTotal::" + movie.getId();
		if (!redisTemplate.hasKey(totalKey)) {
			createTotalRatingCache(movie.getId());
		}

		// 해당 영화의 해당 유저에 대한 조회용 평점이 캐시에 없다면 캐시 생성
		String personalKey = "ratingPersonal::" + movie.getId() + "::" + member.getId();
		if (!redisTemplate.hasKey(personalKey)) {
			createPersonalRatingCache(movie.getId(), member.getId());
		}

		// 새로 입력한 평점 캐시에 등록
		String key = "ratingAdd::" + movie.getId() + "::" + member.getId();
		if (redisTemplate.hasKey(key)) {
			updateRatingCache(key, totalKey, dto);
		} else {
			// 캐시에 평점 등록 정보가 없다면 위에서 만든 조회용 평점을 이용해 새로 등록한다.
			createRatingCache(key, personalKey, totalKey, dto);
		}

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus_code(200);
		responseDto.setMessage("평점 등록 성공.");

		return responseDto;
	}

	/**
	 * 평점 조회
	 */
	@Override
	public ResponseDto findRating(Long memberId, Long movieId) {
		Movie movie = movieRepository.findById(movieId)
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 영화 입니다."));
		Optional<Member> member = memberRepository.findById(memberId);

		RatingResponseDto ratingResponseDto = new RatingResponseDto();

		ratingResponseDto.setMovieId(movie.getId());
		// 해당 영화의 조회용 전체 평점이 캐시에 없다면 캐시 생성
		String totalKey = "ratingTotal::" + movie.getId();
		if (!redisTemplate.hasKey(totalKey)) {
			createTotalRatingCache(movie.getId());
		}
		ratingResponseDto.setRatingTotal(getRatingTotalFromCache(movie.getId()));

		if (member.isPresent()) {
			ratingResponseDto.setMemberId(member.get().getId());
			// 해당 영화의 해당 유저에 대한 조회용 평점이 캐시에 없다면 캐시 생성
			String personalKey = "ratingPersonal::" + movie.getId() + "::" + member.get().getId();
			if (!redisTemplate.hasKey(personalKey)) {
				createPersonalRatingCache(movie.getId(), member.get().getId());
			}
			ratingResponseDto.setRatingPersonal(getRatingPersonalFromCache(movie.getId(), member.get().getId()));
		}

		ResponseDto responseDto = new ResponseDto();
		responseDto.setData(200);
		responseDto.setMessage("평점 조회 성공");
		responseDto.setData(ratingResponseDto);

		return responseDto;
	}

	private RatingDto getRatingPersonalFromCache(Long movieId, Long memberId) {
		ListOperations listOperations = redisTemplate.opsForList();
		String key = "ratingPersonal::" + movieId + "::" + memberId;
		List<String> ratingFromRedis = listOperations.range(key, 0, 5);
		RatingDto ratingPersonalDto = RatingDto.builder()
			.movieId(movieId)
			.memberId(memberId)
			.story(Long.parseLong(ratingFromRedis.get(0).toString()))
			.acting(Long.parseLong(ratingFromRedis.get(1).toString()))
			.direction(Long.parseLong(ratingFromRedis.get(2).toString()))
			.visual(Long.parseLong(ratingFromRedis.get(3).toString()))
			.sound(Long.parseLong(ratingFromRedis.get(4).toString()))
			.total(Long.parseLong(ratingFromRedis.get(5).toString()))
			.build();

		return ratingPersonalDto;
	}

	private RatingDto getRatingTotalFromCache(Long movieId) {
		ListOperations listOperations = redisTemplate.opsForList();
		String key = "ratingTotal::" + movieId;
		List<String> ratingFromRedis = listOperations.range(key, 0, 6);
		RatingDto ratingTotalDto = RatingDto.builder()
			.movieId(movieId)
			.story(Long.parseLong(ratingFromRedis.get(0).toString()))
			.acting(Long.parseLong(ratingFromRedis.get(1).toString()))
			.direction(Long.parseLong(ratingFromRedis.get(2).toString()))
			.visual(Long.parseLong(ratingFromRedis.get(3).toString()))
			.sound(Long.parseLong(ratingFromRedis.get(4).toString()))
			.total(Long.parseLong(ratingFromRedis.get(5).toString()))
			.cnt(Long.parseLong(ratingFromRedis.get(6).toString()))
			.build();

		return ratingTotalDto;
	}

	private void updatePersonalRatingCache(RatingCreateReq dto) {
		String key = "ratingPersonal::" + dto.getMovieId() + "::" + dto.getMemberId();

		ListOperations listOperations = redisTemplate.opsForList();
		listOperations.set(key, 0, String.valueOf(dto.getStory()));
		listOperations.set(key, 1, String.valueOf(dto.getActing()));
		listOperations.set(key, 2, String.valueOf(dto.getDirection()));
		listOperations.set(key, 3, String.valueOf(dto.getVisual()));
		listOperations.set(key, 4, String.valueOf(dto.getSound()));
		listOperations.set(key, 5, String.valueOf(dto.getTotal()));

		listOperations.getOperations().expire(key, Duration.ofMinutes(5));
	}

	private void createPersonalRatingCache(Long movieId, Long memberId) {
		RatingEntity myRatingFromDB = ratingRepository.findPersonal(movieId, memberId);

		// DB에서 해당 유저의 해당 영화에 대한 평점을 가져와 조회용 평점을 만든다.
		// DB에 정보가 없다면 각 항목을 0으로 초기화하여 만든다.
		ListOperations listOperations = redisTemplate.opsForList();
		String key = "ratingPersonal::" + movieId + "::" + memberId;
		if (myRatingFromDB != null) {
			listOperations.rightPush(key, String.valueOf(myRatingFromDB.getStory()));
			listOperations.rightPush(key, String.valueOf(myRatingFromDB.getActing()));
			listOperations.rightPush(key, String.valueOf(myRatingFromDB.getDirection()));
			listOperations.rightPush(key, String.valueOf(myRatingFromDB.getVisual()));
			listOperations.rightPush(key, String.valueOf(myRatingFromDB.getSound()));
			listOperations.rightPush(key, String.valueOf(myRatingFromDB.getTotal()));
		} else {
			listOperations.rightPushAll(key, String.valueOf(-1), String.valueOf(-1), String.valueOf(-1),
				String.valueOf(-1),
				String.valueOf(-1), String.valueOf(-1));
		}
		listOperations.getOperations().expire(key, Duration.ofMinutes(5));
	}

	private void createTotalRatingCache(long movieId) {
		long[] result = new long[7];

		// 해당 영화의 평점 등록으로 캐시에 대기중인 유저를 list에 담는다.
		// list에 있는 유저를 제외한 평점 정보를 db에서 가져온다.
		// 가져온 db의 정보와 캐시에 있는 정보를 합쳐 조회용 전체 평점을 만든다.
		Set<String> redisRatingKeys = redisTemplate.keys("ratingAdd::" + String.valueOf(movieId));
		List<Long> memberList = new ArrayList<>();
		redisRatingKeys.forEach(data -> {
			memberList.add(Long.parseLong(data.split("::")[2]));

			List<String> ratingFromRedis = redisTemplate.opsForList().range(data, 0, 5);
			for (int i = 0; i < 6; i++) {
				result[i] += Long.parseLong(ratingFromRedis.get(5 - i));
			}
		});
		System.out.println(redisRatingKeys.size());
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

	private void updateRatingCache(String key, String totalKey, RatingCreateReq dto) {
		// 캐시에 등록된 평점을 이용하여 전체 평점을 갱신한다.
		ListOperations listOperations = redisTemplate.opsForList();
		List<String> myRating = listOperations.range(key, 0, 5);
		List<String> totalRating = listOperations.range(totalKey, 0, 6);
		for (int i = 0; i < 6; i++) {
			listOperations.set(totalKey, i, calcNewRating(myRating, totalRating, dto, i));
		}
		listOperations.getOperations().expire(totalKey, Duration.ofMinutes(5));

		// 평점 입력을 위한 캐시를 등록한다.
		listOperations.set(key, 0, String.valueOf(dto.getStory()));
		listOperations.set(key, 1, String.valueOf(dto.getActing()));
		listOperations.set(key, 2, String.valueOf(dto.getDirection()));
		listOperations.set(key, 3, String.valueOf(dto.getVisual()));
		listOperations.set(key, 4, String.valueOf(dto.getSound()));
		listOperations.set(key, 5, String.valueOf(dto.getTotal()));

		// 개인 평점을 업데이트한다.
		updatePersonalRatingCache(dto);
	}

	private void createRatingCache(String key, String personalKey, String totalKey, RatingCreateReq dto) {
		ListOperations listOperations = redisTemplate.opsForList();
		List<String> totalRating = listOperations.range(totalKey, 0, 6);
		List<String> personalRating = listOperations.range(personalKey, 0, 5);

		// 캐시의 조회용 개인 평점에 등록된 데이터가 유효한 데이터인지 판단하여 전체 평점을 수정한다.
		List<String> myRating = new ArrayList<>();
		if (Long.parseLong(personalRating.get(0).toString()) >= 0) {
			myRating.add(personalRating.get(0).toString());
			myRating.add(personalRating.get(1).toString());
			myRating.add(personalRating.get(2).toString());
			myRating.add(personalRating.get(3).toString());
			myRating.add(personalRating.get(4).toString());
			myRating.add(personalRating.get(5).toString());
		} else {
			for (int i = 0; i < 6; i++) {
				myRating.add(String.valueOf(0));
			}
			listOperations.set(totalKey, 6, String.valueOf(Long.valueOf(totalRating.get(6).toString()) + 1));
		}

		// 새로 입력받은 정보로 갱신한다.
		for (int i = 0; i < 6; i++) {
			listOperations.set(totalKey, i, calcNewRating(myRating, totalRating, dto, i));
		}
		listOperations.getOperations().expire(totalKey, Duration.ofMinutes(5));

		// 평점 등록을 위한 캐시에 등록한다.
		listOperations.rightPush(key, String.valueOf(dto.getStory()));
		listOperations.rightPush(key, String.valueOf(dto.getActing()));
		listOperations.rightPush(key, String.valueOf(dto.getDirection()));
		listOperations.rightPush(key, String.valueOf(dto.getVisual()));
		listOperations.rightPush(key, String.valueOf(dto.getSound()));
		listOperations.rightPush(key, String.valueOf(dto.getTotal()));

		updatePersonalRatingCache(dto);
	}

	private String calcNewRating(List<String> myRating, List<String> totalRating, RatingCreateReq dto, int i) {
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

}
