package moonrise.pjt1.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
@Entity
@Setter @Getter
public class PartyInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_info_id")
    private Long id;
    private int likeCnt;
    private int viewCnt;
    private int commentCnt;


    public PartyInfo() {
        this.likeCnt = 0;
        this.viewCnt = 0;
        this.commentCnt = 0;
    }
}
