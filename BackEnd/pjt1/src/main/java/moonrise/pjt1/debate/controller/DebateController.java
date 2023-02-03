package moonrise.pjt1.debate.controller;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.Debate;
import moonrise.pjt1.debate.service.DebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/debate")
public class DebateController {

    private final DebateService debateService;

    @Autowired
    public DebateController(DebateService debateService) {
        this.debateService = debateService;
    }

    @GetMapping("/check")
    public String check() {
        return "hi";
    }

    /**
     * 글쓰기
     */
    @PostMapping("/create")
    public ResponseEntity create(long movieId, long memberId, String title, String description, String img, int maxppl) {
        LocalDateTime time = LocalDateTime.now();
        DebateDto dto = DebateDto.builder()
                .title(title)
                .description(description)
                .img(img)
                .maxppl(maxppl)
                .createDate(time)
                .memberId(memberId)
                .movieId(movieId)
                .build();
        debateService.saveDebate(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 전체목록
     */
    @GetMapping
    public ResponseEntity getDebateAll() {
        Iterable<Debate> debatelist = debateService.getDebateAll();
        return ResponseEntity.status(HttpStatus.OK).body(debatelist);
    }

    /**
     * 상세보기
     */
    @GetMapping("/find{id}")
    public ResponseEntity getDebateById(@PathVariable long id) {
        Optional<Debate> debateEntity = debateService.getDebateById(id);
        return ResponseEntity.status(HttpStatus.OK).body(debateEntity);
    }

    /**
     * 수정 : 제목, 내용, 이미지만
     */
    @PutMapping("/update{id}")
    public ResponseEntity update(@PathVariable long id, long movieId, long memberId, String title, String description, String img) {
        DebateDto dto = DebateDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .img(img)
                .movieId(movieId)
                .memberId(memberId)
                .build();
        debateService.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    /**
     * 삭제 : delete문 1로 바꾸기
     */
    @PutMapping("/delete{id}")
    public ResponseEntity delete(@PathVariable long id) {
        debateService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
    }
}
