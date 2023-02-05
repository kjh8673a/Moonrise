package moonrise.pjt3.debate.entity;

import javax.persistence.*;

@Entity
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String writer;
    private String content;
    private int group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debate_id")
    private Debate debate;
}
