package moonrise.pjt2.member.model.service;

import moonrise.pjt2.member.model.entity.Gender;
import moonrise.pjt2.member.model.entity.Member;
import moonrise.pjt2.member.model.entity.MemberInfo;
import moonrise.pjt2.member.model.entity.Profile;
import moonrise.pjt2.member.model.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void save(){
        Profile profile = new Profile("동동이", "M");
        MemberInfo memberInfo = new MemberInfo();

        Member member = new Member();
        member.addId(2652473320L);

        member.addProfile(profile);
        member.addMemberInfo(memberInfo);

        memberRepository.save(member);

        //find Member
        Member findMember = memberRepository.findById(member.getId()).get();

        Assertions.assertEquals(2652473320L, findMember.getId());
        Assertions.assertEquals("동동이", findMember.getProfile().getNickname());
        Assertions.assertEquals(0, findMember.getMemberInfo().getBannedCnt());
    }
}