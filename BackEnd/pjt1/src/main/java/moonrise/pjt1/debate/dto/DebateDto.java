package moonrise.pjt1.debate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DebateDto {
    private long debate_id;
    private String debate_title;
    private String debate_description;
    private String debate_img;
    private String debate_create;
    private int debate_maxppl;
    private int debate_nowppl;
    private boolean debate_delete;
    private long movie_id;
    private long member_id;

    @Builder

    public DebateDto(long debate_id, String debate_title, String debate_description, String debate_img,
                     String debate_create, int debate_maxppl, int debate_nowppl, boolean debate_delete,
                     long movie_id, long member_id) {
        this.debate_id = debate_id;
        this.debate_title = debate_title;
        this.debate_description = debate_description;
        this.debate_img = debate_img;
        this.debate_create = debate_create;
        this.debate_maxppl = debate_maxppl;
        this.debate_nowppl = debate_nowppl;
        this.debate_delete = debate_delete;
        this.movie_id = movie_id;
        this.member_id = member_id;
    }
}
