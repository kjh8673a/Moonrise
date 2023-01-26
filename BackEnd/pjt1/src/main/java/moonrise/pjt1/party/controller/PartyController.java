package moonrise.pjt1.party.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.party.service.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController @RequestMapping("/party")
@Log4j2
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    @GetMapping("/read")
    public ResponseEntity<Map<String, Object>> read(Long partyId){
        partyService.readParty(partyId);

        return null;
    }

}
