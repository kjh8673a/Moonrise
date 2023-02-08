package moonrise.pjt3.debate.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "debate_info")
public class DebateInfo {

    @Id @Column(name = "debate_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int chatCnt;
    private int nowppl;

    public DebateInfo() {
        this.chatCnt = 0;
        this.nowppl = 0;
    }
}
