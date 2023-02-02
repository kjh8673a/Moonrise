package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.entity.PartyComment;
import moonrise.pjt1.party.entity.PartyJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyJoinRepository extends JpaRepository<PartyJoin,Long> {
}
