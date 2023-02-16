package moonrise.pjt1.rating.service;

import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.entity.RatingEntity;

import java.util.List;

public interface RatingService {
    //평점 생성
    RatingEntity createRating(long movieId, long memberId, RatingDto ratingDto);

    //평점 수정
    long[] updateRating(long ratingId, RatingDto ratingDto);

    //전체 평점 조회
    List<Long> findRating(long movieId);

    //개인 평점 조회
    List<Long> findPersonal(long movieId, long memberId);

    //캐시에 추가
    long[] createToCache(long movieId);

    //캐시에 업데이트
    List<Long> addToCache(String key, RatingDto ratingDto);
}
