package moonrise.pjt1.debate.repository;

import moonrise.pjt1.debate.entity.DebateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebateRepository extends JpaRepository<DebateEntity, Long> {
}
