package moonrise.pjt1.party.repository;

import moonrise.pjt1.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party,Long> {
}
