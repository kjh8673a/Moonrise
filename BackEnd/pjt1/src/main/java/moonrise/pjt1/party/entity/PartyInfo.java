package moonrise.pjt1.party.entity;

import javax.persistence.*;

@Entity
public class PartyInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_info_id")
    private Long id;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;
}
