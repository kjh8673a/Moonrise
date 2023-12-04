package moonrise.pjt1.rating.service;

import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.rating.dto.RatingDto;
import moonrise.pjt1.rating.entity.RatingEntity;
import moonrise.pjt1.rating.request.RatingCreateReq;

import java.util.List;

public interface RatingService {
	ResponseDto createRating(RatingCreateReq dto);

    ResponseDto findRating(Long memberId, Long movieId);

	void updateRating(long movieId, long memberId, List<String> ratingList);

}
