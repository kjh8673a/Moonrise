package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item {
    private String director;
    private String actor;


}
