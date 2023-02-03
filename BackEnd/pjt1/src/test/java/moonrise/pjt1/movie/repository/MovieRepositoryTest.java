package moonrise.pjt1.movie.repository;

import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.party.entity.Party;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @Test
    @Transactional
    void 영화_아이디에_따른_소모임(){
        //given
        Long movieId = 1L;

        //when
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Movie movie = movieOptional.get();

        List<Party> parties = movie.getParties();

        //then
        for (Party party : parties) {
            System.out.println("party = " + party.getTitle());
        }

        Assertions.assertEquals(3,parties.size());
    }
}