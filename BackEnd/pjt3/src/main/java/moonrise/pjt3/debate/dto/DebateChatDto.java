package moonrise.pjt3.debate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString @NoArgsConstructor
public class DebateChatDto {
    private Long debateId;
    private String content;
    private String writer;
    @Builder
    public DebateChatDto(Long debateId, String content, String writer) {
        this.debateId = debateId;
        this.content = content;
        this.writer = writer;
    }
}
