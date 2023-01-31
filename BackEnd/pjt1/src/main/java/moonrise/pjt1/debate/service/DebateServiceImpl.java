package moonrise.pjt1.debate.service;

import moonrise.pjt1.debate.dto.DebateDto;
import moonrise.pjt1.debate.entity.DebateEntity;
import moonrise.pjt1.debate.repository.DebateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DebateServiceImpl implements DebateService{
    private DebateRepository debateRepository;

    @Autowired
    public DebateServiceImpl(DebateRepository debateRepository) {
        this.debateRepository = debateRepository;
    }

    @Override
    public DebateEntity saveDebate(DebateDto dto) {
        DebateEntity debateEntity = DebateEntity.builder()
                .debate_create(dto.getDebate_create())
                .debate_title(dto.getDebate_title())
                .debate_description(dto.getDebate_description())
                .debate_img(dto.getDebate_img())
                .debate_maxppl(dto.getDebate_maxppl())
                .kakao_id(dto.getKakao_id())
                .movie_id(dto.getMovie_id())
                .build();
        debateRepository.save(debateEntity);
        return debateEntity;
    }

    @Override
    public Iterable<DebateEntity> getDebateAll() {
        return debateRepository.findAll();
    }

    @Override
    public Optional<DebateEntity> getDebateById(int debate_id) {
        return debateRepository.findById(debate_id);
    }

    @Override
    public void update(DebateDto dto) {
        Optional<DebateEntity> debate = debateRepository.findById(dto.getDebate_id());
        DebateEntity debateEntity=debate.get();
        debateEntity.setDebate_title(dto.getDebate_title());
        debateEntity.setDebate_description(dto.getDebate_description());
        debateEntity.setDebate_img((dto.getDebate_img()));
        debateRepository.save(debateEntity);
    }

    @Override
    public void delete(int debate_id) { //update문으로 boolean처리
        Optional<DebateEntity> debate = debateRepository.findById(debate_id);
        DebateEntity debateEntity=debate.get();
        debateEntity.setDebate_delete(true);
        debateRepository.save(debateEntity);
    }
}
