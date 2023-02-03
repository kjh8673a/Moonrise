package moonrise.pjt3.member.repository;

import moonrise.pjt3.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
