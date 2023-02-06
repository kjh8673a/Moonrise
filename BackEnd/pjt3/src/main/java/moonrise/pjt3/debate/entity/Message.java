package moonrise.pjt3.debate.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @NoArgsConstructor
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String writer;
    private String content;
    private int groupNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debate_id")
    private Debate debate;
    @Builder
    public Message(String writer, String content, int groupNum, Debate debate) {
        this.writer = writer;
        this.content = content;
        this.groupNum = groupNum;
        this.debate = debate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", group=" + groupNum +
                ", debate=" + debate +
                '}';
    }
}
