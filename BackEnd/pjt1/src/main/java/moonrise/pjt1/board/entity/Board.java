package moonrise.pjt1.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonrise.pjt1.board.dto.BoardDto;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.movie.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Movie movie;

    @OneToOne(fetch = FetchType.LAZY)
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

    public static Board createBoard(BoardDto boardDto, Member member, Movie movie, BoardInfo boardInfo){
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setDateTime(boardDto.getDateTime());
        board.setMember(member);
        board.setBoardInfo(boardInfo);
        board.setMovie(movie);
        return board;
    }

}
