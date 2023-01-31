package moonrise.pjt1.party.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
@Entity
public class PartyInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_info_id")
    private Long id;

    private int likeCnt;
    private int viewCnt;
    private int commentCnt;

    @OneToOne(mappedBy = "partyInfo",fetch = FetchType.LAZY)
    private Party party;

    public PartyInfo() {
        this.likeCnt = 0;
        this.viewCnt = 0;
        this.commentCnt = 0;
    }
}
