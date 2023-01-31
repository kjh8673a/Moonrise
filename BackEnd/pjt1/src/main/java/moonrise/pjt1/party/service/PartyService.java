package moonrise.pjt1.party.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.dto.*;
import moonrise.pjt1.party.entity.Party;
import moonrise.pjt1.party.entity.PartyComment;
import moonrise.pjt1.party.entity.PartyJoin;
import moonrise.pjt1.party.repository.PartyCommentRepository;
import moonrise.pjt1.party.repository.PartyJoinRepository;
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
    private final PartyJoinRepository partyJoinRepository;

    public Map<String,Object> readParty(Long partyId) {
        Optional<Party> findParty = partyRepository.findById(partyId);
        Party party = findParty.get();
        List<PartyComment> partyComments = party.getPartyComments();
        List<PartyJoin> partyJoins = party.getPartyJoins();
        Map<String,Object> result = new HashMap<>();
        if(findParty.isPresent()){
            PartyReadResponseDto partyReadResponseDto = new PartyReadResponseDto(party.getId(),party.getTitle(),party.getContent(),party.getPartyDate(),
                    party.getPartyPeople(),party.getLocation(),party.getPartyStatus(),party.getMember().getId(),
                    party.getMovie().getId(),partyJoins,partyComments,party.getDeadLine());
            result.put("findParty",partyReadResponseDto);
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

    public Long createJoin(PartyJoinCreateDto partyJoinCreateDto) {
        Optional<Member> findMember = memberRepository.findById(partyJoinCreateDto.getMemberId());
        Optional<Party> findParty = partyRepository.findById(partyJoinCreateDto.getPartyId());

        PartyJoin partyJoin = PartyJoin.createPartyJoin(partyJoinCreateDto,findMember.get(),findParty.get());
        partyJoinRepository.save(partyJoin);

        return partyJoin.getId();
    }
}
