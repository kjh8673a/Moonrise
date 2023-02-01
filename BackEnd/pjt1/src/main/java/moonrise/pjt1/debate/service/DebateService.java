package moonrise.pjt1.debate.service;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.DebateEntity;

import java.util.Optional;

public interface DebateService {
    DebateEntity saveDebate(DebateDto debateDto);

    Iterable<DebateEntity> getDebateAll();

    Optional<DebateEntity> getDebateById(int debate_id);

    void update(DebateDto debateDto);

    void delete(int debate_id);
<<<<<<< HEAD

=======
>>>>>>> daf06450ded54db8b51c4ad53cf423a9e4b0c2c9
}
