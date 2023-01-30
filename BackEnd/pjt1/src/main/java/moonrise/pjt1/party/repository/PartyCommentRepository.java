package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.entity.Party;
import moonrise.pjt1.party.entity.PartyComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyCommentRepository extends JpaRepository<PartyComment,Long> {

}
