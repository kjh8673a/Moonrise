package moonrise.pjt1.party.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.dto.PartyCommentCreateDto;
import moonrise.pjt1.party.dto.PartyCreateDto;
import moonrise.pjt1.party.dto.PartyListResponseDto;
import moonrise.pjt1.party.entity.Party;
import moonrise.pjt1.party.entity.PartyComment;
import moonrise.pjt1.party.repository.PartyCommentRepository;
import moonrise.pjt1.party.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final PartyCommentRepository partyCommentRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    public Map<String,Object> readParty(Long partyId) {
        Optional<Party> findParty = partyRepository.findById(partyId);
        Map<String,Object> result = new HashMap<>();
        if(findParty.isPresent()){
            result.put("findParty",findParty.get());
        }
        return result;
    }
    public Map<String,Object> listParty(Long movieId) {
        Map<String,Object> result = new HashMap<>();
        List<PartyListResponseDto> partyList = partyRepository.findPartyList(movieId);
        result.put("findParties",partyList);
        return result;
    }

    public Long createParty(PartyCreateDto partyCreateDto) {
        Optional<Member> findMember = memberRepository.findById(partyCreateDto.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(partyCreateDto.getMovieId());

        Party party = Party.createParty(partyCreateDto, findMember.get(), findMovie.get());
        partyRepository.save(party);
        return party.getId();
    }

    public Long createComment(PartyCommentCreateDto partyCommentCreateDto) {
        Optional<Member> findMember = memberRepository.findById(partyCommentCreateDto.getMemberId());
        Optional<Party> findParty = partyRepository.findById(partyCommentCreateDto.getPartyId());

        PartyComment partyComment = PartyComment.createPartyComment(partyCommentCreateDto, findParty.get(), findMember.get());
        partyCommentRepository.save(partyComment);
        return partyComment.getId();
    }
}
