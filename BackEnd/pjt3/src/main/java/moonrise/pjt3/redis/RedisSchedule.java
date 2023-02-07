package moonrise.pjt3.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.entity.DebateInfo;
import moonrise.pjt3.debate.repository.DebateInfoRepository;
import moonrise.pjt3.debate.repository.MessageRepository;
import moonrise.pjt3.debate.service.DebateService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisSchedule {
    private final DebateInfoRepository debateInfoRepository;
    private final MessageRepository messageRepository;
    private final RedisTemplate redisTemplate;
    private final DebateService debateService;
    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteChatCacheFromRedis() throws IOException {
        Set<String> redisKeys = redisTemplate.keys("debateChat*");
        Iterator<String> it = redisKeys.iterator();
        ObjectMapper mapper = new ObjectMapper();

        while (it.hasNext()) {
            String data = it.next();
            Long debateId = Long.parseLong(data.split("::")[1]);
            String debateChatDtos = redisTemplate.opsForList().range(data,0,-1).toString();
            log.info(debateChatDtos+"오오오오파싱 됩니까?");
            List<DebateChatDto> dtos = Arrays.asList(mapper.readValue(debateChatDtos, DebateChatDto[].class));
            System.out.println(dtos.size() + " : " +dtos);
            Integer groupNum = messageRepository.findMaxGroupId(debateId);
            if(groupNum == null){
                debateService.saveRdbChat(dtos,1);
            }
            else{
                debateService.saveRdbChat(dtos,groupNum+1);
            }
            redisTemplate.delete(data);
            redisTemplate.delete("debateChat::"+debateId);
        }
    }
    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deleteLivePeopleCacheFromRedis() {
        Set<String> redisKeys = redisTemplate.keys("debateLivePeopleCnt*");
        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long debateId = Long.parseLong(data.split("::")[1]);
            int debateLivePeopleCnt = Integer.parseInt((String) redisTemplate.opsForValue().get(data));
            DebateInfo debateInfo = debateInfoRepository.findById(debateId).get();
            debateInfo.setNowppl(debateLivePeopleCnt);
            redisTemplate.delete(data);
            redisTemplate.delete("debateLivePeopleCnt::"+debateId);
        }
    }
}
