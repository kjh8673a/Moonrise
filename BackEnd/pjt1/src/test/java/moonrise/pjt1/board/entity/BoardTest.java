package moonrise.pjt1.board.entity;

import moonrise.pjt1.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void testBoard(){

    }
}