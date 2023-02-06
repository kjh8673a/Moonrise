package moonrise.pjt3.debate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data @ToString
public class DebateChatDto {
    private Long debateId;
    private String content;
    private String writer;
    private int group;
    @Builder
    public DebateChatDto(Long debateId, String content, String writer, int group) {
        this.debateId = debateId;
        this.content = content;
        this.writer = writer;
        this.group = group;
    }
}
