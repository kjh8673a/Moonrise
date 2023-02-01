package moonrise.pjt2.member.model.service;

import lombok.RequiredArgsConstructor;
import moonrise.pjt2.member.controller.MemberJoinRequestDto;
import moonrise.pjt2.member.exception.NotExistMemberException;
import moonrise.pjt2.member.model.entity.Member;
import moonrise.pjt2.member.model.entity.Profile;
import moonrise.pjt2.member.model.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(MemberService.class);
    public void join(MemberJoinRequestDto dto){
        // dto를 통한 엔티티 만들기
        Profile memberProfile = new Profile(dto.getUsername(), dto.getNickname(), dto.getGender(), dto.getPhone());

        // Member에 profile 매핑
        Member member = new Member();
        member.addId(dto.getUser_id());
        member.addProfile(memberProfile);

        memberRepository.save(member);
    }
    public boolean check_enroll_member(Long userId){
        //카카오 고유 번호를 받아 디비에 있는지 확인
        Optional<Member> member = memberRepository.findById(userId);
        logger.info("member : {}", member);

        if(member.isEmpty()){
//            throw new NotExistMemberException("회원이 존재하지 않습니다.");
            return true;
        }
        return false;
    }
}
