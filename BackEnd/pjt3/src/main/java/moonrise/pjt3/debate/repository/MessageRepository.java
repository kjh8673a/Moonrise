package moonrise.pjt3.debate.repository;

import moonrise.pjt3.debate.dto.DebateChatDto;
import moonrise.pjt3.debate.entity.Debate;
import moonrise.pjt3.debate.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(value = "select MAX(m.groupNum) from Message m where m.debate.id = :debateId")
    Integer findMaxGroupId(@Param("debateId") Long debateId);
    @Query(value = "select new moonrise.pjt3.debate.dto.DebateChatDto(m.debateId, m.content, m.writer)" +
            " from Message m where m.debate.id = :debateId and m.groupNum = :groupNum")
    List<DebateChatDto> findBYGroupNumAndDebateId(@Param("debateId")Long debateId, @Param("groupNum")int groupNum);
}
