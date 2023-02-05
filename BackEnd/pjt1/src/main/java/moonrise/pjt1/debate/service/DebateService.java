package moonrise.pjt1.debate.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.debate.dto.DebateCreateDto;
import moonrise.pjt1.debate.dto.DebateListResponseDto;
import moonrise.pjt1.debate.dto.DebateReadResponseDto;
import moonrise.pjt1.debate.entity.Debate;
import moonrise.pjt1.debate.entity.DebateInfo;
import moonrise.pjt1.debate.repository.DebateRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DebateService {

    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final DebateRepository debateRepository;
    public Map<String, Object> listDebate(Long movieId) {
        Map<String,Object> result = new HashMap<>();
        List<Debate> dabateList = debateRepository.findDebateList(movieId);
        List<DebateListResponseDto> debateListResponseDtos = new ArrayList<>();
        for (Debate debate : dabateList) {
            DebateListResponseDto debateListResponseDto = DebateListResponseDto.builder()
                            .debateId(debate.getId())
                            .title(debate.getTitle())
                            .writer(debate.getMember().getProfile().getNickname())
                            .maxppl(debate.getMaxppl())
                            .nowppl(debate.getDebateInfo().getNowppl())
                            .debateStatus(debate.getDebateStatus())
                            .build();
            debateListResponseDtos.add(debateListResponseDto);
        }
        result.put("findParties",debateListResponseDtos);
        return result;
    }
    public Long createParty(DebateCreateDto debateCreateDto) {
        Optional<Member> findMember = memberRepository.findById(debateCreateDto.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(debateCreateDto.getMovieId());
        DebateInfo debateInfo = new DebateInfo();
        Debate debate = Debate.builder()
                .debateCreateDto(debateCreateDto)
                .member(findMember.get())
                .movie(findMovie.get())
                .debateInfo(debateInfo)
                .build();
        debateRepository.save(debate);
        return debate.getId();
    }

    public Map<String, Object> readDebate(Long debateId) {
        Optional<Debate> findDebate = debateRepository.findById(debateId);
        Debate debate = findDebate.get();
        Map<String,Object> result = new HashMap<>();
        if(findDebate.isPresent()){
            DebateReadResponseDto debateReadResponseDto = DebateReadResponseDto.builder()
                    .debateId(debate.getId())
                    .title(debate.getTitle())
                    .description(debate.getDescription())
                    .writer(debate.getMember().getProfile().getNickname())
                    .debateStatus(debate.getDebateStatus())
                    .maxppl(debate.getMaxppl())
                    .nowppl(debate.getDebateInfo().getNowppl())
                    .build();
            result.put("readDebate",debateReadResponseDto);
        }
        return result;
    }
}
