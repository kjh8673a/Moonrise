package moonrise.pjt1.member;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.entity.Profile;
import moonrise.pjt1.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void save(){
        Profile profile = new Profile("박윤지","날카로운비평가", "W", "01072911424");
        Member member = new Member();
        member.addId(2643552103L);
        member.addProfile(profile);

        memberRepository.save(member);

        //find Member
        Member findMember = memberRepository.findById(member.getId()).get();

        Assertions.assertEquals(2643552103L, findMember.getId());
        Assertions.assertEquals(profile.getUsername(), findMember.getProfile().getUsername());
    }
}