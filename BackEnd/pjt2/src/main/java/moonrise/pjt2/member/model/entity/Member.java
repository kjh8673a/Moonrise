package moonrise.pjt2.member.model.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Member {
    @Id
    @Column(name = "member_id")
    private int id;
    private String nickname;
    private String phone;
}
