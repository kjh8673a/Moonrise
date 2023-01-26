package moonrise.pjt1.party.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.party.entity.Party;
import moonrise.pjt1.party.entity.PartyComment;
import moonrise.pjt1.party.repository.PartyCommentRepository;
import moonrise.pjt1.party.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final PartyCommentRepository partyCommentRepository;
    public Map<String,Object> readParty(Long partyId) {
        Optional<Party> response = partyRepository.findById(partyId);
        Map<String,Object> result = new HashMap<>();
        if(response.isPresent()){
            result.put("findParty",response.get());
        }
        return result;
    }
}
