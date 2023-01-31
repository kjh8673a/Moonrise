package moonrise.pjt1.member;

import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.entity.Profile;
import moonrise.pjt1.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void save(){
        Profile profile = new Profile("김동률","동동이", "M", "01033334444");
        Member member = new Member();
        member.addId(2630362777L);
        member.addProfile(profile);

        memberRepository.save(member);

        //find Member
        Member findMember = memberRepository.findById(member.getId()).get();

        Assertions.assertEquals(2630362777L, findMember.getId());
        Assertions.assertEquals(profile.getUsername(), findMember.getProfile().getUsername());
    }
}