package moonrise.pjt1.rating.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingCreateResponseDto {
	private Long movieId;
	private Long memberId;
	private RatingDto ratingTotal;
	private RatingDto ratingPersonal;

	@Builder
	public RatingCreateResponseDto(Long movieId, Long memberId, RatingDto ratingTotal, RatingDto ratingPersonal) {
		this.memberId = memberId;
		this.movieId = movieId;
		this.ratingTotal = ratingTotal;
		this.ratingPersonal = ratingPersonal;
	}

}
