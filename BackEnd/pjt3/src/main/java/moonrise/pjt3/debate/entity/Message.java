package moonrise.pjt3.debate.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @NoArgsConstructor @Data
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String writer;
    private String content;
    private String imagePath;
    private int groupNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debate_id")
    private Debate debate;
    @Builder
    public Message(String writer, String content, int groupNum, Debate debate,String imagePath) {
        this.writer = writer;
        this.content = content;
        this.groupNum = groupNum;
        this.debate = debate;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", groupNum=" + groupNum +
                '}';
    }
}
