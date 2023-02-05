package moonrise.pjt3.debate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.dto.DebateCreateDto;
import moonrise.pjt3.debate.dto.DebateListResponseDto;
import moonrise.pjt3.debate.dto.DebateReadResponseDto;
import moonrise.pjt3.debate.entity.Debate;
import moonrise.pjt3.debate.entity.DebateInfo;
import moonrise.pjt3.debate.repository.DebateRepository;
import moonrise.pjt3.member.entity.Member;
import moonrise.pjt3.member.repository.MemberRepository;
import moonrise.pjt3.movie.entity.Movie;
import moonrise.pjt3.movie.repository.MovieRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class DebateService {
    private final RedisTemplate redisTemplate;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final DebateRepository debateRepository;

    public void saveCacheChat(DebateChatDto debateChatDto){ //채팅 들어오면 캐시에 저장
        String key = "debateChat::"+debateChatDto.getDebateId();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        ListOperations<String, Object> chatOperations = redisTemplate.opsForList();

//        chatOperations.rightPush(key,debateChatDto.toString());

        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(debateChatDto);
            log.info(value);
            chatOperations.rightPush(key,value);
        } catch(Exception e){
            log.error(e);
        }
    }
}
