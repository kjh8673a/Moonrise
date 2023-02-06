
package moonrise.pjt1.party.entity;

import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.dto.PartyCommentCreateDto;
import moonrise.pjt1.party.dto.PartyCreateDto;
import moonrise.pjt1.party.repository.PartyRepository;
import moonrise.pjt1.party.repository.PartyRepositoryTest;
import moonrise.pjt1.party.service.PartyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Map;

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
    }
    @Test
    void listParty(){
        PageRequest pageable = PageRequest.of(0, 8, Sort.by("id").descending());
        ResponseDto responseDto = partyService.listParty(1L, pageable);
        System.out.println(responseDto);
    }

    @Test
    void readParty(){

    }

    @Test
    void commentWriteParty(){

    }
}
