package moonrise.pjt3.debate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class DebateChatResponseDto {
    private Long debateId;
    private String content;
    private String writer;
    private String imagePath;
    @Builder
    public DebateChatResponseDto(Long debateId, String content, String writer,String imagePath) {
        this.debateId = debateId;
        this.content = content;
        this.writer = writer;
        this.imagePath = imagePath;
    }
}
