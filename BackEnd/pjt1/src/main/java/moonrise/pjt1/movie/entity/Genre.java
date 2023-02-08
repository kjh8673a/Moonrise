package moonrise.pjt1.movie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter @NoArgsConstructor
public class Genre {

    @Id
    private int id;
    private String genreName;

}
