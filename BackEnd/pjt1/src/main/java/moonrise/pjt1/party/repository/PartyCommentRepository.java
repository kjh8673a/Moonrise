package moonrise.pjt1.party.repository;

import io.lettuce.core.dynamic.annotation.Param;
import moonrise.pjt1.party.entity.PartyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartyCommentRepository extends JpaRepository<PartyComment,Long> {

    @Query(value = "select pc from PartyComment as pc where pc.party.id =:partyId and pc.partyCommentStatus = 'NORMAL' order by pc.groupId, pc.id")
    List<PartyComment> getCommentList(@Param("partyId") Long partyId);

    @Query(value = "select pc from PartyComment pc where pc.id = :commentId or pc.groupId = :commentId")
    List<PartyComment> getChangeCommentList(@Param("commentId") Long commentId);
}
