package moonrise.pjt2.member.model.service;

import moonrise.pjt2.member.model.entity.Gender;
import moonrise.pjt2.member.model.entity.Member;
import moonrise.pjt2.member.model.entity.Profile;
import moonrise.pjt2.member.model.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void save(){
        Profile profile = new Profile("정상민","맛덩이", "M", "01011112222");
        Member member = new Member();
        member.addId(2630362777L);
        member.addProfile(profile);

        memberRepository.save(member);
    }
}