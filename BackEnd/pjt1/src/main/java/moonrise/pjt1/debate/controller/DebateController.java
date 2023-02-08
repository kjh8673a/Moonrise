package moonrise.pjt1.debate.controller;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.DebateEntity;
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
    public ResponseEntity create(long movie_id, long member_id, String title, String description, String img, int maxppl) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        DebateDto dto = DebateDto.builder()
                .debate_title(title)
                .debate_description(description)
                .debate_img(img)
                .debate_maxppl(maxppl)
                .debate_create(time)
                .member_id(member_id)
                .movie_id(movie_id)
                .build();
        debateService.saveDebate(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 전체목록
     */
    @GetMapping
    public ResponseEntity getDebateAll() {
        Iterable<DebateEntity> debatelist = debateService.getDebateAll();
        return ResponseEntity.status(HttpStatus.OK).body(debatelist);
    }

    /**
     * 상세보기
     */
    @GetMapping("/find{id}")
    public ResponseEntity getDebateById(@PathVariable long id) {
        Optional<DebateEntity> debateEntity = debateService.getDebateById(id);
        return ResponseEntity.status(HttpStatus.OK).body(debateEntity);
    }

    /**
     * 수정 : 제목, 내용, 이미지만
     */
    @PutMapping("/update{id}")
    public ResponseEntity update(@PathVariable long id, long movie_id, long member_id, String title, String description, String img) {
        DebateDto dto = DebateDto.builder()
                .debate_id(id)
                .debate_title(title)
                .debate_description(description)
                .debate_img(img)
                .movie_id(movie_id)
                .member_id(member_id)
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
