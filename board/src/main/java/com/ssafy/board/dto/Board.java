package com.ssafy.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Board {
    private int id;
    private String title;
    private String description;

    @Builder
    public Board(int id, String title, String description){
        this.id=id;
        this.title=title;
        this.description=description;
    }
}
