package moonrise.pjt1.debate.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "debate")
public class DebateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int debate_id;

    @Column(nullable = true)
    private String debate_title;

    @Column(nullable = true)
    private String debate_description;

    @Column(nullable = true) //사진은 선택사항으로
    private String debate_img;

    @Column(nullable = true)
    private String debate_create;

    @Column(nullable = true)
    private int debate_maxppl;

    @Column(nullable = true)
    private int debate_nowppl;

    @Column(nullable = true)
    @ColumnDefault("false")//초기값 false
    private boolean debate_delete;

    @Column(nullable = true)
    private int movie_id;

    @Column(nullable = true)
    private int kakao_id;

    @Builder
    public DebateEntity(int debate_id, String debate_title, String debate_description,
                        String debate_img, String debate_create, int debate_maxppl,
                        int debate_nowppl, boolean debate_delete, int movie_id, int kakao_id) {
        this.debate_id = debate_id;
        this.debate_title = debate_title;
        this.debate_description = debate_description;
        this.debate_img = debate_img;
        this.debate_create = debate_create;
        this.debate_maxppl = debate_maxppl;
        this.debate_nowppl = debate_nowppl;
        this.debate_delete = debate_delete;
        this.movie_id = movie_id;
        this.kakao_id = kakao_id;
    }
}
