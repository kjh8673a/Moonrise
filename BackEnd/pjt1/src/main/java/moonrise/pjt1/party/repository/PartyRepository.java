package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.dto.PartyListResponseDto;
import moonrise.pjt1.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party,Long> {
    @Query(value = "select new moonrise.pjt1.party.dto.PartyListResponseDto" +
            "(p.id,p.title,p.partyPeople,p.location) from Party p where p.movie.id = :movieId")
    List<PartyListResponseDto> findPartyList(@Param("movieId") Long movieId);
}
