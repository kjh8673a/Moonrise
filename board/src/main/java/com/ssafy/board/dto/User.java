package com.ssafy.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String uid;
    private String name;
    private String pass;

    @Builder
    public User(String uid, String name, String pass){
        this.uid=uid;
        this.name=name;
        this.pass=pass;
    }
}
