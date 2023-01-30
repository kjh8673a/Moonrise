
package moonrise.pjt1.party.entity;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.dto.PartyCommentCreateDto;
import moonrise.pjt1.party.dto.PartyCreateDto;
import moonrise.pjt1.party.repository.PartyRepository;
import moonrise.pjt1.party.service.PartyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Map;

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
    @Autowired
    PartyService partyService;
    @Test
    void writeParty(){
        PartyCreateDto partyCreateDto = new PartyCreateDto(201611222L,4L,"노실분구합니다","어서오세요",
                LocalDateTime.now(),5,"대전시유성구",false);
        partyService.createParty(partyCreateDto);
    }
    @Test
    void listParty(){
        Map<String, Object> listParty = partyService.listParty(1L);
        System.out.println(listParty.get("findParties"));
    }

    @Test
    void readParty(){
        Map<String, Object> readParty = partyService.readParty(13L);
        System.out.println(readParty.get("findParty"));
    }

    @Test
    void commentWriteParty(){
        PartyCommentCreateDto partyCommentCreateDto = new PartyCommentCreateDto(13L,201611222L,
                "댓글작성~",true);
        partyService.createComment(partyCommentCreateDto);
    }
}
