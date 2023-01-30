package moonrise.pjt1.member.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.board.entity.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Profile profile;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    public void addProfile(Profile memberProfile) {
        this.profile = memberProfile;
    }
    public void addId(Long userId) {
        this.id = userId;
    }
}