package moonrise.pjt1.member.repository;

import moonrise.pjt1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
