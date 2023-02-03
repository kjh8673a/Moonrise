package moonrise.pjt1.debate.service;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.Debate;

import java.util.Optional;

public interface DebateService {
    Debate saveDebate(DebateDto debateDto);

    Iterable<Debate> getDebateAll();

    Optional<Debate> getDebateById(long debate_id);

    void update(DebateDto debateDto);

    void delete(long debate_id);
}
