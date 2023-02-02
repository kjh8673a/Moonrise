package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.entity.PartyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyInfoRepository extends JpaRepository<PartyInfo,Long> {
    @Query(value = "select pi.viewCnt from PartyInfo pi where pi.id = :partyId")
    int findPartyViewCnt(@Param("partyId") Long partyId);
}
