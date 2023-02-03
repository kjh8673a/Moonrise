package moonrise.pjt1.debate.service;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.Debate;
import moonrise.pjt1.debate.entity.DebateInfo;
import moonrise.pjt1.debate.entity.DebateStatus;
import moonrise.pjt1.debate.repository.DebateRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DebateServiceImpl implements DebateService {

    private final DebateRepository debateRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public DebateServiceImpl(DebateRepository debateRepository, MovieRepository movieRepository,
                             MemberRepository memberRepository) {
        this.debateRepository = debateRepository;
        this.movieRepository = movieRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Debate saveDebate(DebateDto dto) {
        Optional<Member> findMember = memberRepository.findById(dto.getMemberId());
        Optional<Movie> findMovie = movieRepository.findById(dto.getMovieId());
        DebateInfo debateInfo = new DebateInfo();
        Debate debate = Debate.builder()
                .createDate(dto.getCreateDate())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .img(dto.getImg())
                .maxppl(dto.getMaxppl())
                .movie(findMovie.get())
                .member(findMember.get())
                .debateInfo(debateInfo)
                .build();
        debateRepository.save(debate);
        return debate;
    }

    @Override
    public Iterable<Debate> getDebateAll() {
        return debateRepository.findAll();
    }

    @Override
    public Optional<Debate> getDebateById(long debate_id) {
        return debateRepository.findById(debate_id);
    }

    @Override @Transactional
    public void update(DebateDto dto) {
        Optional<Debate> debate = debateRepository.findById(dto.getId());
        Debate debateEntity = debate.get();
        debateEntity.setTitle(dto.getTitle());
        debateEntity.setDescription(dto.getDescription());
        debateEntity.setImg((dto.getImg()));
    }

    @Override @Transactional
    public void delete(long debateId) { //update문으로 boolean처리
        Optional<Debate> debate = debateRepository.findById(debateId);
        Debate debateEntity = debate.get();
        debateEntity.setDebateStatus(DebateStatus.삭제);
    }
}
