package moonrise.pjt1.party.repository;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.entity.Party;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
public class PartyRepositoryTest {
    @Autowired
    PartyRepository partyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MovieRepository movieRepository;

    @Test
    @Transactional
    void 소모임_추가(){
        Party party = new Party();

        Member member = memberRepository.findById(2643550085L).get();
        Movie movie = movieRepository.findById(100402L).get();

        int size = movie.getParties().size();

        party.setPartyDate(LocalDateTime.now());
        party.setMember(member);
        party.setLocation("대전시 유성구 노은동");
        party.setPartyPeople(5);
        party.setTitle("위스키 한잔 ㄱ??");
        party.setContent("");

        party.setMovie(movie);

        //when
        partyRepository.save(party);


        //then
        Movie findMovie = movieRepository.findById(100402L).get();
        int findSize = findMovie.getParties().size();
        Assertions.assertEquals(size+1, findSize);
    }
}
