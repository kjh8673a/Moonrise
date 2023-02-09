package moonrise.pjt2.member.model.repository;

import moonrise.pjt2.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m" +
            " join fetch m.profile " +
            " join fetch m.memberInfo where m.id = :aLong")
    Optional<Member> findMemberById(Long aLong);

    Optional<Member> findById(Long aLong);
}
