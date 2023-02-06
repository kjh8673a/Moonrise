package moonrise.pjt1.board.entity;

import moonrise.pjt1.board.repository.BoardRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.entity.Profile;
import moonrise.pjt1.movie.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    EntityManager em;

    @Test
    void 게시글작성(){
        // 회원 생성
        Profile profile = new Profile("찰스", "M");
        Member member = new Member();
        member.addProfile(profile);

        em.persist(member);

        // 영화 생성
        Movie movie = new Movie();
        movie.setTitle("해리포터");

        em.persist(movie);

        //게시글 생성
        Board board = new Board();
        board.setTitle("해리포터 본 사람??");
        board.setContent("오랜만에 봤는데 재밌네요...");

        // 매핑해서 넣어보기
        board.setMember(member);
        board.setMovie(movie);

        em.persist(board);

        // when
        Board findBoard = em.find(Board.class, board.getId());

        Member findMember = findBoard.getMember();

        //then
        Assertions.assertEquals(member.getId(), findMember.getId());
    }

    @Test
    void 회원별_게시글_찾기(){
        // 회원 생성
        Profile profile = new Profile("찰스", "M");

        Member member = new Member();

        member.addProfile(profile);

        em.persist(member);

        // 영화 생성
        Movie movie = new Movie();
        movie.setTitle("해리포터");

        em.persist(movie);

        //게시글 생성
        Board board = new Board();

        //게시글 기본 내용
        board.setTitle("해리포터 본 사람??");
        board.setContent("오랜만에 봤는데 재밌네요...");

        // 매핑해서 넣어보기
        board.setMovie(movie);
        board.addMember(member);

        em.persist(board);

        //then
        // 회원으로 게시글 찾기
        Member findMember = em.find(Member.class, 1L);
        List<Board> boards = findMember.getBoards();

        Assertions.assertEquals(1, boards.size());

    }


}