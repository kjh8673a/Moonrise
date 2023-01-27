package moonrise.pjt1.party.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.party.repository.PartyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;

    public void readParty(Long partyId) {
        partyRepository.findById(partyId);
    }
}
