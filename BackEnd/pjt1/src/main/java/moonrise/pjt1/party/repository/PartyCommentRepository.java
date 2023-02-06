package moonrise.pjt1.party.repository;

import io.lettuce.core.dynamic.annotation.Param;
import moonrise.pjt1.party.entity.PartyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartyCommentRepository extends JpaRepository<PartyComment,Long> {
    @Query(value = "select pc from PartyComment as pc where pc.party.id =:partyId order by pc.groupId")
    List<PartyComment> getCommentList(@Param("partyId") Long partyId);
}
