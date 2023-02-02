package moonrise.pjt1.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    private final PartyInfoRepository partyInfoRepository;
    private final RedisTemplate redisTemplate;

    @Transactional
    @Scheduled(cron = "0 0/3 * * * ?")
    public void deleteViewCntCacheFromRedis() {
        Set<String> redisKeys = redisTemplate.keys("partyViewCnt*");
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long partyId = Long.parseLong(data.split("::")[1]);
            int viewCnt = Integer.parseInt((String) redisTemplate.opsForValue().get(data));
            PartyInfo partyInfo = partyInfoRepository.findById(partyId).get();
            partyInfo.setViewCnt(viewCnt);
            redisTemplate.delete(data);
            redisTemplate.delete("party::"+partyId);
        }
    }
}
