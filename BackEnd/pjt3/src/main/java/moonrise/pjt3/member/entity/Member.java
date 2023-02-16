package moonrise.pjt3.member.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import moonrise.pjt3.board.entity.Board;
import moonrise.pjt3.party.entity.Party;

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.NORMAL;    // default

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Party> parties = new ArrayList<>();

    public void addProfile(Profile memberProfile) {
        this.profile = memberProfile;
    }
    public void addId(Long userId) {
        this.id = userId;
    }
}