package com.ssafy.board.jpa;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CustomRepository {
    @PersistenceContext
    private EntityManager em;

    public  BoardEntity findById(int id){
        return em.find(BoardEntity.class, id);
    }
}
