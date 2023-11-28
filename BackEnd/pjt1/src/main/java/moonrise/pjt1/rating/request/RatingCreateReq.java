package moonrise.pjt1.rating.request;

import lombok.Data;

@Data
public class RatingCreateReq {
	private Long memberId;
	private Long movieId;

	private long story;  //스토리
	private long acting; //연기
	private long direction; //연출
	private long visual; //영상미
	private long sound;  //사운드

	private long total;  //총점
}
