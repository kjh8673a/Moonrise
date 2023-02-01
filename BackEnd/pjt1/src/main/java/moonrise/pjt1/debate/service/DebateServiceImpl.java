package moonrise.pjt1.debate.service;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.DebateEntity;
import moonrise.pjt1.debate.entity.DebateInfoEntity;
import moonrise.pjt1.debate.repository.DebateRepository;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DebateEntity saveDebate(DebateDto dto) {
        Optional<Member> findMember = memberRepository.findById(dto.getMember_id());
        Optional<Movie> findMovie = movieRepository.findById(dto.getMovie_id());
        DebateInfoEntity debateInfoEntity = new DebateInfoEntity();
        DebateEntity debateEntity = DebateEntity.builder()
                .debate_create(dto.getDebate_create())
                .debate_title(dto.getDebate_title())
                .debate_description(dto.getDebate_description())
                .debate_img(dto.getDebate_img())
                .debate_maxppl(dto.getDebate_maxppl())
                .movie(findMovie.get())
                .member(findMember.get())
                .debateInfoEntity(debateInfoEntity)
                .build();
        debateRepository.save(debateEntity);
        return debateEntity;
    }

    @Override
    public Iterable<DebateEntity> getDebateAll() {
        return debateRepository.findAll();
    }

    @Override
    public Optional<DebateEntity> getDebateById(long debate_id) {
        return debateRepository.findById(debate_id);
    }

    @Override
    public void update(DebateDto dto) {
        Optional<DebateEntity> debate = debateRepository.findById(dto.getDebate_id());
        Optional<Member> findMember = memberRepository.findById(dto.getMember_id());
        Optional<Movie> findMovie = movieRepository.findById(dto.getMovie_id());
        DebateEntity debateEntity = debate.get();
        debateEntity.setDebate_title(dto.getDebate_title());
        debateEntity.setDebate_description(dto.getDebate_description());
        debateEntity.setDebate_img((dto.getDebate_img()));
        debateEntity.setMovie(findMovie.get());
        debateEntity.setMember(findMember.get());
        debateRepository.save(debateEntity);
    }

    @Override
    public void delete(long debate_id) { //update문으로 boolean처리
        Optional<DebateEntity> debate = debateRepository.findById(debate_id);
        DebateEntity debateEntity = debate.get();
        debateEntity.setDebate_delete(true);
        debateRepository.save(debateEntity);
    }
}
