package moonrise.pjt2.member.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonrise.pjt2.member.dto.MemberJoinDto;
import moonrise.pjt2.member.dto.MemberUpdateDto;
import moonrise.pjt2.member.dto.ResponseDto;
import moonrise.pjt2.member.exception.NotExistMemberException;
import moonrise.pjt2.member.model.entity.Member;
import moonrise.pjt2.member.model.entity.MemberInfo;
import moonrise.pjt2.member.model.entity.Profile;
import moonrise.pjt2.member.model.repository.MemberRepository;
import moonrise.pjt2.member.model.repository.ProfileRepository;
import moonrise.pjt2.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;



    public ResponseDto join(MemberJoinDto dto, Long user_id){
        // dto를 통한 엔티티 만들기
        Profile memberProfile = new Profile(dto.getNickname(), dto.getGender());

        // Member에 profile 매핑
        Member member = new Member();
        member.addId(user_id);
        MemberInfo memberInfo = new MemberInfo();

        //Genres 스트링화
        List<String> genres = dto.getGenres();
        StringBuilder sb = new StringBuilder();
        for (String genre : genres) {
            sb.append(genre + ",");
        }
        memberInfo.setLikeGenre(sb.toString());

        member.addMemberInfo(memberInfo);
        member.addProfile(memberProfile);

        dto.setImagePath(memberProfile.getProfile_image_path());
        memberRepository.save(member);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(dto);
        responseDto.setStatus_code(200);
        responseDto.setMessage("회원 가입 완료");

        return responseDto;
    }
    @Transactional
    public ResponseDto memberInfoUpdate(String atk, MemberUpdateDto memberUpdateDto){
        ResponseDto responseDto = new ResponseDto();

        Long user_id = HttpUtil.parseToken(atk);

        //Null 처리
        if(user_id == null){
            log.error("userId == null error");

            responseDto.setStatus_code(400);
            responseDto.setMessage("토큰 파싱과정에서 오류");
            return responseDto;
        }

        Member member = memberRepository.findById(user_id).get();

        MemberInfo memberInfo = member.getMemberInfo();

        //Genres 스트링화
        List<String> genres = memberUpdateDto.getGenres();
        StringBuilder sb = new StringBuilder();
        for (String genre : genres) {
            sb.append(genre + ",");
        }
        memberInfo.setLikeGenre(sb.toString());

        // 프로필 수정
        Profile profile = member.getProfile();
        profile.setProfile_image_path(memberUpdateDto.getImagePath());
        profile.setNickname(memberUpdateDto.getNickname());

        responseDto.setStatus_code(200);
        responseDto.setData(memberUpdateDto);
        responseDto.setMessage("회원 수정이 완료되었습니다.");

        return responseDto;
    }
    public boolean check_enroll_member(Long userId){
        //카카오 고유 번호를 받아 디비에 있는지 확인
        Optional<Member> member = memberRepository.findById(userId);
        log.info("member : {}", member);

        if(member.isEmpty()){
//            throw new NotExistMemberException("회원이 존재하지 않습니다.");
            return true;
        }
        return false;
    }
    @Transactional
    public ResponseDto findMember(String atk){
        ResponseDto responseDto = new ResponseDto();
        Long user_id = HttpUtil.parseToken(atk);

        if(user_id == null){
            log.error("userId == null error");

            responseDto.setStatus_code(400);
            responseDto.setMessage("토큰 파싱과정에서 오류");
            return responseDto;
        }
        Optional<Member> find = memberRepository.findMemberById(user_id);

        if(!find.isPresent()){
            log.error("findMember : 회원 정보가 없습니다.");
            responseDto.setMessage("회원정보가 없습니다.");
            responseDto.setStatus_code(400);

            return responseDto;
        }
        responseDto.setData(find.get());
        responseDto.setMessage("회원 정보 로드 완료");
        responseDto.setStatus_code(200);

        return responseDto;
    }
    @Transactional
    public MemberJoinDto findMemberAll(Long userId){
        Optional<Member> m = memberRepository.findMemberById(userId);
        if(!m.isPresent()){
            throw new NotExistMemberException("회원이 존재하지 않습니다.");
        }
        Member findMember = m.get();
        MemberJoinDto dto = new MemberJoinDto();
        MemberInfo memberInfo = findMember.getMemberInfo();
        Profile profile = findMember.getProfile();

        dto.setGender(profile.getGender().toString());
        dto.setNickname(profile.getNickname());
        dto.setImagePath(profile.getProfile_image_path());
        String likeGenre = memberInfo.getLikeGenre();

        if(likeGenre == null){
            return dto;
        }
        StringTokenizer st = new StringTokenizer(likeGenre,",");
        ArrayList<String> genres = new ArrayList<>();
        while(st.hasMoreTokens()){
            genres.add(st.nextToken());
        }
        dto.setGenres(genres);
//        dto.setGenres(findMember.getMemberInfo().getLikeGenre().toString());
        return dto;
    }
    public ResponseDto nicknameCheck(String nickname){
        Optional<Profile> findProfile = profileRepository.findByNickname(nickname);

        ResponseDto responseDto = new ResponseDto();
        if(!findProfile.isPresent()){
            responseDto.setStatus_code(200);
            responseDto.setMessage("사용중인 닉네임이 없습니다.");
            return responseDto;
        }
        log.info("nicknameCheck : {}", findProfile.get().getNickname());
        log.info("이미 사용중인 닉네임 입니다.");
        responseDto.setStatus_code(400);
        responseDto.setMessage("이미 사용중인 닉네임 입니다.");
        return responseDto;
    }
}
