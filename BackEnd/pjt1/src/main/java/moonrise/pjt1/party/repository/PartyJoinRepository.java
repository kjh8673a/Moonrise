package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.dto.PartyJoinListDto;
import moonrise.pjt1.party.entity.PartyJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyJoinRepository extends JpaRepository<PartyJoin,Long> {

    @Query(value = "select pj" +
            " from PartyJoin pj join fetch pj.party" +
            " where pj.member.id = :userId")
    List<PartyJoin> findMyJoinList(@Param("userId") Long userId);
}
