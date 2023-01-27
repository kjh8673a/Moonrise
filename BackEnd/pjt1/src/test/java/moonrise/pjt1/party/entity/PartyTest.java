package moonrise.pjt1.party.entity;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.repository.PartyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PartyTest {
    @Autowired
    PartyRepository partyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    EntityManager em;
    @Test
    void testParty(){
        Member member = new Member();
        member.setNickname("맛덩이");
        member.setGender("남");

        Movie movie = new Movie();
        movie.setTitle("하리포타");

        memberRepository.save(member);
        movieRepository.save(movie);
        em.flush();
        em.clear();

        Party party = new Party();
        party.setMember(member);
        party.setMovie(movie);
        party.setTitle("모집합네다~");
        partyRepository.save(party);

    }
}