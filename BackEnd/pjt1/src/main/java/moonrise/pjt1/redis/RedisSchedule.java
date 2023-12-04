package moonrise.pjt1.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.board.entity.Board;
import moonrise.pjt1.board.entity.BoardInfo;
import moonrise.pjt1.board.repository.BoardInfoRepository;
import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.debate.entity.DebateInfo;
import moonrise.pjt1.debate.repository.DebateInfoRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.entity.MemberInfo;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.entity.PartyInfo;
import moonrise.pjt1.party.repository.PartyInfoRepository;
import moonrise.pjt1.rating.entity.RatingEntity;
import moonrise.pjt1.rating.repository.RatingCustomRepository;
import moonrise.pjt1.rating.repository.RatingRepository;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisSchedule {
    private final RatingRepository ratingRepository;
    private final RatingCustomRepository ratingCustomRepository;
    private final MovieRepository movieRepository;
    private final DebateInfoRepository debateInfoRepository;
    private final PartyInfoRepository partyInfoRepository;
    private final BoardInfoRepository boardInfoRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final RedisTemplate redisTemplate;
    
    /**
     * 캐시에서 게시글 조회수 정보 DB로 저장
     */
    @Transactional
    @Scheduled(cron = "0 0/3 * * * ?")
    public void deleteViewCntCacheFromRedis() {
        log.info("캐시에서 게시글 조회수 정보 DB로 저장");
        Set<String> redisKeys = redisTemplate.keys("boardViewCnt*");
        Objects.requireNonNull(redisKeys).forEach(data -> {
            Long boardId = Long.parseLong(data.split("::")[1]);
            Board board = boardRepository.findById(boardId).get();
            Long viewCnt = board.getBoardInfo().getViewCnt();
            viewCnt += Long.parseLong(redisTemplate.opsForValue().get(data).toString());
            board.getBoardInfo().setViewCnt(viewCnt);
            redisTemplate.delete(data);
        });
    }

    /**
     * 캐시에서 게시글 좋아요 정보 DB로 저장
     */
    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteLikeCacheFromRedis() {
        log.info("캐시에서 게시글 좋아요 정보 DB로 저장");
        Set<String> redisLikeKeys = redisTemplate.keys("boardLikeAdd*");
        Objects.requireNonNull(redisLikeKeys).forEach(data -> {
            Long boardId = Long.parseLong(data.split("::")[1]);
            Long cnt = redisTemplate.opsForSet().size(data);
            Board board = boardRepository.findById(boardId).get();
            board.getBoardInfo().addLikeCnt(cnt);

            Set<String> redisUsers = redisTemplate.opsForSet().members(data);
            redisUsers.stream().forEach(user -> {
                Long userId = Long.parseLong(user);
                Optional<Member> member = memberRepository.findById(userId);
                String s = member.get().getMemberInfo().getLikeBoard() + boardId + ",";
                member.get().getMemberInfo().setLikeBoard(s);
                redisTemplate.opsForSet().remove(data, user);
            });
        });

        redisLikeKeys = redisTemplate.keys("boardLikeDel*");
        Objects.requireNonNull(redisLikeKeys).forEach(data -> {
            Long boardId = Long.parseLong(data.split("::")[1]);
            Long cnt = redisTemplate.opsForSet().size(data);
            Board board = boardRepository.findById(boardId).get();
            board.getBoardInfo().subtractLikeCnt(cnt);

            Set<String> redisUsers = redisTemplate.opsForSet().members(data);
            redisUsers.stream().forEach(user -> {
                Long userId = Long.parseLong(user);
                Optional<Member> member = memberRepository.findById(userId);
                StringBuilder sb = new StringBuilder(member.get().getMemberInfo().getLikeBoard());
                String s = boardId + ",";
                sb.delete(sb.indexOf(s), sb.indexOf(s) + s.length());
                member.get().getMemberInfo().setLikeBoard(sb.toString());
                redisTemplate.opsForSet().remove(data, user);
            });
        });
    }

    /**
     * 캐시에서 게시글 북마크 정보 DB로 저장
     */
    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteBookmarkCacheFromRedis() {
        log.info("캐시에서 게시글 북마크 정보 DB로 저장");
        Set<String> redisBookmarkKeys = redisTemplate.keys("boardBookmarkAdd*");
        Objects.requireNonNull(redisBookmarkKeys).forEach(data -> {
            Long boardId = Long.parseLong(data.split("::")[1]);
            Set<String> redisUsers = redisTemplate.opsForSet().members(data);
            redisUsers.stream().forEach(user -> {
                Long userId = Long.parseLong(user);
                Optional<Member> member = memberRepository.findById(userId);
                String s = member.get().getMemberInfo().getBookmarkBoard() + boardId + ",";
                member.get().getMemberInfo().setBookmarkBoard(s);
                redisTemplate.opsForSet().remove(data, user);
            });
        });

        redisBookmarkKeys = redisTemplate.keys("boardLikeDel*");
        Objects.requireNonNull(redisBookmarkKeys).forEach(data -> {
            Long boardId = Long.parseLong(data.split("::")[1]);
            Set<String> redisUsers = redisTemplate.opsForSet().members(data);
            redisUsers.stream().forEach(user -> {
                Long userId = Long.parseLong(user);
                Optional<Member> member = memberRepository.findById(userId);
                StringBuilder sb = new StringBuilder(member.get().getMemberInfo().getBookmarkBoard());
                String s = boardId + ",";
                sb.delete(sb.indexOf(s), sb.indexOf(s) + s.length());
                member.get().getMemberInfo().setBookmarkBoard(sb.toString());
                redisTemplate.opsForSet().remove(data, user);
            });
        });
    }

    /**
     * 캐시에서 평점 정보 DB로 저장
     */
    @Transactional
    @Scheduled(cron = "0 0/7 * * * ?")
    public void deleteRatingCacheFromRedis() {
        log.info("캐시에서 평점 정보 DB로 저장");
        List<RatingEntity> ratingInsertList = new ArrayList<>();
        List<RatingEntity> ratingUpdateList = new ArrayList<>();
        Set<String> redisRatingKeys = redisTemplate.keys("ratingAdd*");
        redisRatingKeys.forEach(data -> {
            Long movieId = Long.parseLong(data.split("::")[1]);
            Long userId = Long.parseLong(data.split("::")[2]);
            List<String> ratingFromRedis = redisTemplate.opsForList().range(data, 0, 5);
            RatingEntity myRating = ratingRepository.findPersonal(movieId, userId);
            RatingEntity newRating = RatingEntity.builder()
                .story(Long.parseLong(ratingFromRedis.get(0).toString()))
                .acting(Long.parseLong(ratingFromRedis.get(1).toString()))
                .direction(Long.parseLong(ratingFromRedis.get(2).toString()))
                .visual(Long.parseLong(ratingFromRedis.get(3).toString()))
                .sound(Long.parseLong(ratingFromRedis.get(4).toString()))
                .total(Long.parseLong(ratingFromRedis.get(5).toString()))
                .movie(movieRepository.findById(movieId).get())
                .member(memberRepository.findById(userId).get())
                .build();
            if(myRating == null) {
                ratingInsertList.add(newRating);
            }else {
                ratingUpdateList.add(newRating);
            }

            redisTemplate.delete(data);
        });

        ratingCustomRepository.batchInsert(ratingInsertList);
        ratingCustomRepository.batchUpdate(ratingUpdateList);
    }

}
