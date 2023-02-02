package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.dto.PartyListResponseDto;
import moonrise.pjt1.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party,Long> {
    @Query(value = "select p from Party p join fetch p.partyInfo where p.movie.id = :movieId")
    List<Party> findPartyList(@Param("movieId") Long movieId);
}
