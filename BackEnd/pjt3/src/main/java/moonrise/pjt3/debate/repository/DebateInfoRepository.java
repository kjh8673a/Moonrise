package moonrise.pjt3.debate.repository;

import moonrise.pjt3.debate.entity.DebateInfo;
import moonrise.pjt3.debate.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DebateInfoRepository extends JpaRepository<DebateInfo,Long> {

    @Query(value = "select di.nowppl from DebateInfo di where di.id = :debateId")
    int findDebateLivePeople(@Param("debateId") Long debateId);
}
