package moonrise.pjt1.debate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "debate_info")
public class DebateInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long debateinfo_id;
    private int debate_like;
    private int debate_viewCnt;
    private int debate_commentCnt;

    @OneToOne(mappedBy = "debateInfoEntity", fetch = FetchType.LAZY)
    private DebateEntity debateEntity;

}
