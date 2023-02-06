package moonrise.pjt3.debate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.dto.DebateCreateDto;
import moonrise.pjt3.debate.dto.DebateListResponseDto;
import moonrise.pjt3.debate.dto.DebateReadResponseDto;
import moonrise.pjt3.debate.entity.Debate;
import moonrise.pjt3.debate.entity.DebateInfo;
import moonrise.pjt3.debate.entity.Message;
import moonrise.pjt3.debate.repository.DebateRepository;
import moonrise.pjt3.debate.repository.MessageRepository;
import moonrise.pjt3.member.entity.Member;
import moonrise.pjt3.member.repository.MemberRepository;
import moonrise.pjt3.movie.entity.Movie;
import moonrise.pjt3.movie.repository.MovieRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Log4j2
public class DebateService {
    private final RedisTemplate redisTemplate;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final DebateRepository debateRepository;
    private final MessageRepository messageRepository;
    public void saveCacheChat(DebateChatDto debateChatDto){ //채팅 들어오면 캐시에 저장
        String key = "debateChat::"+debateChatDto.getDebateId();
        ListOperations<String, Object> chatOperations = redisTemplate.opsForList();
        String value = "";
        try { //
            ObjectMapper mapper = new ObjectMapper();
            value = mapper.writeValueAsString(debateChatDto);
            log.info(value);
        } catch(Exception e){
            log.error(e);
        }
        if(chatOperations.size(key) == 0){
            chatOperations.rightPush(key,value);
            redisTemplate.expire(key,12,TimeUnit.HOURS);
            log.info("키값 없어서 키 생성하면서 expire 설정");
        }
        else{
            chatOperations.rightPush(key,value);
        }
    }

    public void saveRdbChat(List<DebateChatDto> debateChatDtos, int groupNum){
        log.info(groupNum+"메시지 저장");
        for (DebateChatDto debateChatDto : debateChatDtos) {
            Debate debate = debateRepository.findById(debateChatDto.getDebateId()).get();
            Message message = Message.builder()
                    .content(debateChatDto.getContent())
                    .debate(debate)
                    .groupNum(groupNum)
                    .writer(debateChatDto.getWriter())
                    .build();
            messageRepository.save(message);
            log.info(message);
        }
    }

    public Map<String, Object> getRecentChat(Long debateId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String key = "debateChat::"+debateId;
        String debateChatDtos = redisTemplate.opsForList().range(key,0,-1).toString();
        log.info(debateChatDtos+"오오오오파싱 됩니까?");
        List<DebateChatDto> dtos = Arrays.asList(mapper.readValue(debateChatDtos, DebateChatDto[].class));
        Map<String,Object> result = new HashMap<>();
        result.put("recentChats",dtos);
        return result;
    }
}
