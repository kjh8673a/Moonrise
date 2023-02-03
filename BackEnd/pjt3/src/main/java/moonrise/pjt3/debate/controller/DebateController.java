package moonrise.pjt3.debate.controller;

import lombok.RequiredArgsConstructor;
import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.dto.DebateCreateDto;
import moonrise.pjt3.debate.service.DebateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/debate")
@RequiredArgsConstructor
public class DebateController {

    private final DebateService debateService;
    @PostMapping("/testtesttest")
    public void message(@RequestBody DebateChatDto debateChatDto){
        System.out.println(debateChatDto);
        debateService.saveCacheChat(debateChatDto);

    }
}
