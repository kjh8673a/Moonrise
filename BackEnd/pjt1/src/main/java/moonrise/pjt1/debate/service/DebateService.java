package moonrise.pjt1.debate.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.debate.dto.DebateCreateDto;
import moonrise.pjt1.debate.dto.DebateListResponseDto;
import moonrise.pjt1.debate.dto.DebateReadResponseDto;
import moonrise.pjt1.debate.entity.Debate;
import moonrise.pjt1.debate.entity.DebateInfo;
import moonrise.pjt1.debate.repository.DebateInfoRepository;
import moonrise.pjt1.debate.repository.DebateRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.util.HttpUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DebateService {
    private final RedisTemplate redisTemplate;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final DebateRepository debateRepository;
    private final DebateInfoRepository debateInfoRepository;
//    public Map<String, Object> listDebate(Long movieId) {
//        Map<String,Object> result = new HashMap<>();
//        List<Debate> dabateList = debateRepository.findDebateList(movieId);
//        List<DebateListResponseDto> debateListResponseDtos = new ArrayList<>();
//        for (Debate debate : dabateList) {
//            DebateListResponseDto debateListResponseDto = DebateListResponseDto.builder()
//                            .debateId(debate.getId())
//                            .title(debate.getTitle())
//                            .writer(debate.getMember().getProfile().getNickname())
//                            .maxppl(debate.getMaxppl())
//                            .nowppl(debate.getDebateInfo().getNowppl())
//                            .debateStatus(debate.getDebateStatus())
//                            .build();
//            debateListResponseDtos.add(debateListResponseDto);
//        }
//        result.put("findParties",debateListResponseDtos);
//        return result;
//    }
    public ResponseDto listDebate(Long movieId, PageRequest pageable) {
        Map<String,Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        Page<Debate> debateList = debateRepository.findDebateList(movieId, pageable);
        result.put("findParties",debateList.get());
        result.put("totalPages", debateList.getTotalPages());

        //responseDto 작성
        responseDto.setMessage("토론방 목록 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    public ResponseDto createDebate(String access_token, DebateCreateDto debateCreateDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);

        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Member> findMember = memberRepository.findById(user_id);
        Optional<Movie> findMovie = movieRepository.findById(debateCreateDto.getMovieId());
        DebateInfo debateInfo = new DebateInfo();
        Debate debate = Debate.builder()
                .debateCreateDto(debateCreateDto)
                .member(findMember.get())
                .movie(findMovie.get())
                .debateInfo(debateInfo)
                .build();
        debateRepository.save(debate);

        //responseDto 작성
        result.put("debate_id",debate.getId());
        responseDto.setMessage("토론방 작성 완료");
        responseDto.setData(result);
        responseDto.setStatus_code(200);

        return responseDto;
    }

    public ResponseDto readDebate(String access_token,Long debateId) {
        Map<String,Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Debate> findDebate = debateRepository.findById(debateId);
        Debate debate = null;
        String key = "debateLivePeopleCnt::"+debateId;
        int debateLivePeople;
        //캐시에 값이 없으면 레포지토리에서 조회, 있으면 캐시 조회.
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(valueOperations.get(key)==null){
            debateLivePeople = debateInfoRepository.findDebateLivePeople(findDebate.get().getDebateInfo().getId());
            valueOperations.set(
                    key,
                    String.valueOf(debateLivePeople),
                    20,
                    TimeUnit.MINUTES);
        }
        else debateLivePeople = Integer.parseInt((String) valueOperations.get(key));
        if(findDebate.isPresent()){
            debate = findDebate.get();
            DebateReadResponseDto debateReadResponseDto = DebateReadResponseDto.builder()
                    .debateId(debate.getId())
                    .title(debate.getTitle())
                    .description(debate.getDescription())
                    .writer(debate.getMember().getProfile().getNickname())
                    .debateStatus(debate.getDebateStatus())
                    .maxppl(debate.getMaxppl())
                    .nowppl(debateLivePeople)
                    .createDate(debate.getCreateDate())
                    .build();
            result.put("readDebate",debateReadResponseDto);
            if(debate.getMember().getId().equals(user_id)) result.put("isWriter",true);
            else result.put("isWriter",false);
        }
        //responseDto 작성
        responseDto.setMessage("토론방 상세보기 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
}
