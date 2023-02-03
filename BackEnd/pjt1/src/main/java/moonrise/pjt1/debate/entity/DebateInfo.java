package moonrise.pjt1.debate.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "debate_info")
public class DebateInfo {

    @Id @Column(name = "debate_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int likeCnt;
    private int viewCnt;
    private int chatCnt;

    public DebateInfo() {
        this.likeCnt = 0;
        this.viewCnt = 0;
        this.chatCnt = 0;
    }
}
