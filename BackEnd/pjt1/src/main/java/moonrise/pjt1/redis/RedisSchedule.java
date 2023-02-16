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
import moonrise.pjt1.party.entity.PartyInfo;
import moonrise.pjt1.party.repository.PartyInfoRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisSchedule {
    private final DebateInfoRepository debateInfoRepository;
    private final PartyInfoRepository partyInfoRepository;
    private final BoardInfoRepository boardInfoRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final RedisTemplate redisTemplate;

    @Transactional
    @Scheduled(cron = "0 0/3 * * * ?")
    public void deleteViewCntCacheFromRedis() {
        log.info("조회수 DB 백업");
        Set<String> redisKeys = redisTemplate.keys("partyViewCnt*");
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long partyId = Long.parseLong(data.split("::")[1]);
            int viewCnt = Integer.parseInt((String) redisTemplate.opsForValue().get(data));
            PartyInfo partyInfo = partyInfoRepository.findById(partyId).get();
            partyInfo.setViewCnt(viewCnt);
            redisTemplate.delete("partyViewCnt::"+partyId);
        }

        redisKeys = redisTemplate.keys("boardViewCnt*");
        it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long boardId = Long.parseLong(data.split("::")[1]);
            int viewCnt = Integer.parseInt((String) redisTemplate.opsForValue().get(data));
            Board board = boardRepository.findById(boardId).get();
            board.getBoardInfo().setViewCnt(viewCnt);
            redisTemplate.delete("boardViewCnt::"+boardId);

        }
    }

    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteLikeCacheFromRedis() {
        log.info("좋아요게시글 DB 백업");
        Set<String> redisKeys = redisTemplate.keys("boardLikeCnt*");
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long boardId = Long.parseLong(data.split("::")[1]);
            int likeCnt = Integer.parseInt((String) redisTemplate.opsForValue().get(data));
            Board board = boardRepository.findById(boardId).get();
            board.getBoardInfo().setLikeCnt(likeCnt);
            redisTemplate.delete("boardLikeCnt::" + boardId);
        }

        redisKeys = redisTemplate.keys("UserBoardLikeList*");
        it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long memberId = Long.parseLong(data.split("::")[1]);
            String likeList = (String) redisTemplate.opsForValue().get(data);
            Member member = memberRepository.findById(memberId).get();
            member.getMemberInfo().setLikeBoard(likeList);
            redisTemplate.delete("UserBoardLikeList::"+memberId);

        }
    }

    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteBookmarkCacheFromRedis() {
        log.info("북마크리스트 DB 백업");
        Set<String> redisKeys = redisTemplate.keys("UserBoardBookMarkList*");
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long memberId = Long.parseLong(data.split("::")[1]);
            String bookmarkList = (String) redisTemplate.opsForValue().get(data);
            Member member = memberRepository.findById(memberId).get();
            member.getMemberInfo().setBookmarkBoard(bookmarkList);
            redisTemplate.delete("UserBoardBookMarkList::"+memberId);
        }
    }
}
