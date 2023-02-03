package moonrise.pjt3.debate.entity;

import javax.persistence.*;

@Entity
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String writer;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Debate debate;
}
