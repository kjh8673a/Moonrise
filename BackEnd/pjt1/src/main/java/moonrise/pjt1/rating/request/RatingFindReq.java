package moonrise.pjt1.rating.request;

import lombok.Data;

@Data
public class RatingFindReq {
	private Long memberId;
	private Long movieId;
}
