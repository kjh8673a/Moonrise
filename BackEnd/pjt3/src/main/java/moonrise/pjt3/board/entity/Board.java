package moonrise.pjt3.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt3.board.dto.BoardCreateDto;
import moonrise.pjt3.member.entity.Member;
import moonrise.pjt3.movie.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static moonrise.pjt3.board.entity.BoardStatus.*;

@Entity
@Table(name = "board")
@Getter @Setter @NoArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "write_date")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_info_id")
    private BoardInfo boardInfo;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardComments = new ArrayList<>();

    //business logic
    public void addMember(Member member){
        this.member = member;       // setMember
        member.getBoards().add(this);   // 작성자에게도 board 리스트에 넣어주기
    }

    public void addMovie(Movie movie){
        this.movie = movie;
        movie.getBoards().add(this);
    }

    public static Board createBoard(BoardCreateDto boardCreateDto, Member member, Movie movie, BoardInfo boardInfo){
        Board board = new Board();
        board.setTitle(boardCreateDto.getTitle());
        board.setContent(boardCreateDto.getContent());
        board.setDateTime(LocalDateTime.now());
        board.setMember(member);
        board.setBoardInfo(boardInfo);
        board.setMovie(movie);
        return board;
    }
//    public void addBoardInfo(BoardInfo boardInfo){
//        this.boardInfo = boardInfo;
//    }

    //  게시글 삭제
    public void cancle(){
        this.boardInfo.setBoardStatus(DELETED);
    }
    public void banned(){
        this.boardInfo.setBoardStatus(BANNED);
    }
    public void normalize(){
        this.boardInfo.setBoardStatus(NORMAL);
    }

}
