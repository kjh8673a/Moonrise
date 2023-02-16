package moonrise.pjt1.rating.service;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.entity.RatingEntity;
import moonrise.pjt1.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, MovieRepository movieRepository, MemberRepository memberRepository, RedisTemplate redisTemplate) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
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
