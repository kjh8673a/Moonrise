package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.entity.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party,Long> {
    @Query(value = "select new moonrise.pjt1.party.dto.PartyListResponseDto" + "(p.id, p.title, p.partyPeople, p.location, p.partyDate, p.partyInfo.viewCnt) from Party p where p.movie.id = :movieId")
    Page<Party> findPartyList(@Param("movieId") Long movieId, Pageable pageable);

//    @Query(value = "select p from Party p join fetch p.partyInfo where p.movie.id = :movieId")
//    List<Party> findPartyList(@Param("movieId") Long movieId);
}
