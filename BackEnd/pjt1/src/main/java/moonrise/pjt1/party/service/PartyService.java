package moonrise.pjt1.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import moonrise.pjt1.commons.response.ResponseDto;
import moonrise.pjt1.member.entity.Member;
import moonrise.pjt1.member.repository.MemberRepository;
import moonrise.pjt1.movie.entity.Movie;
import moonrise.pjt1.movie.repository.MovieRepository;
import moonrise.pjt1.party.dto.*;
import moonrise.pjt1.party.entity.*;
import moonrise.pjt1.party.repository.PartyCommentRepository;
import moonrise.pjt1.party.repository.PartyInfoRepository;
import moonrise.pjt1.party.repository.PartyJoinRepository;
import moonrise.pjt1.party.repository.PartyRepository;
import moonrise.pjt1.util.HttpUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
// token parsing 요청
// member_id 매핑
//DB
//responseDto 작성

@Service
@RequiredArgsConstructor
@Log4j2
public class PartyService {
    private final PartyRepository partyRepository;
    private final PartyCommentRepository partyCommentRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final PartyJoinRepository partyJoinRepository;
    private final PartyInfoRepository partyInfoRepository;
    private final RedisTemplate redisTemplate;
    public ResponseDto readParty(String access_token, Long partyId) {
        Map<String,Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id == 0L){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Party> findParty = partyRepository.findById(partyId);
        Party party = findParty.get();
        Long partyInfoId = party.getPartyInfo().getId();
        //***************redis 캐시서버**********************
        String key = "partyViewCnt::"+partyId;
        //캐시에 값이 없으면 레포지토리에서 조회 있으면 값을 증가시킨다.
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(valueOperations.get(key)==null){
            valueOperations.set(
                    key,
                    String.valueOf(partyInfoRepository.findPartyViewCnt(partyInfoId)+1),
                    20,
                    TimeUnit.MINUTES);
        }
        else{
            valueOperations.increment(key);
        }
        int viewCnt = Integer.parseInt((String) valueOperations.get(key));
        log.info("value:{}",viewCnt);
        //***************redis 캐시서버**********************
        //***************DB 조회**********************
        List<PartyComment> partyComments = partyCommentRepository.getCommentList(partyId);
        List<PartyJoin> partyJoins = party.getPartyJoins();
        List<PartyReadJoinDto> partyJoinAccept = new ArrayList<>();
        List<PartyReadJoinDto> partyJoinWait = new ArrayList<>();
        List<PartyReadJoinDto> partyJoinSurplus = new ArrayList<>();

        boolean flag = false;
        for (PartyJoin partyJoin : partyJoins) {
            PartyReadJoinDto partyReadJoinDto = PartyReadJoinDto.builder()
                    .id(partyJoin.getId())
                    .applier(partyJoin.getApplier())
                    .joinDate(partyJoin.getJoinDate())
                    .partyJoinStatus(partyJoin.getPartyJoinStatus())
                    .message(partyJoin.getMessage())
                    .imagePath(partyJoin.getMember().getProfile().getProfile_image_path())
                    .build();
            if(partyJoin.getPartyJoinStatus().toString().equals("승인대기")) partyJoinWait.add(partyReadJoinDto);
            else if(partyJoin.getPartyJoinStatus().toString().equals("승인")) partyJoinAccept.add(partyReadJoinDto);
            else partyJoinSurplus.add(partyReadJoinDto);

            if(partyJoin.getMember().getId().equals(user_id)){
                result.put("joinStatus",partyJoin.getPartyJoinStatus());
                flag = true;
            }
        }
        if(!flag){
            result.put("joinStatus","신청안함");
        }
        if(user_id.equals(party.getMember().getId())){
            result.put("isWriter",true);
        }
        else {
            result.put("isWriter",false);
        }
        int commentsCnt = partyComments.size();
        int likeCnt = party.getPartyInfo().getLikeCnt();
        if(findParty.isPresent()){
            PartyReadResponseDto partyReadResponseDto = new PartyReadResponseDto(party.getId(),party.getTitle(),party.getContent(),party.getPartyDate(),
                    party.getPartyPeople(),party.getLocation(),party.getPartyStatus(),
                    party.getMovie().getId(),partyComments,party.getDeadLine(), viewCnt, likeCnt,
                    commentsCnt,party.getMember().getProfile().getNickname(),
                    partyJoinAccept,partyJoinWait,partyJoinSurplus,party.getImagePath(),
                    party.getMember().getProfile().getProfile_image_path());
            result.put("findParty",partyReadResponseDto);
        }
        //***************DB 조회**********************
        //responseDto 작성
        responseDto.setMessage("소모임 상세보기 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    public ResponseDto listParty(Long movieId, PageRequest pageable) {
        Map<String,Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        Page<Party> partyList = partyRepository.findPartyList(movieId, pageable);
        List<PartyListResponseDto> findParties = new ArrayList<>();

        for(Party p : partyList){
            int viewCnt = p.getPartyInfo().getViewCnt();
            int commentsCnt = p.getPartyComments().size();
            int likeCnt = p.getPartyInfo().getLikeCnt();
            String nickname = p.getMember().getProfile().getNickname();
            PartyListResponseDto partyListResponseDto = PartyListResponseDto.builder()
                    .imagePath(p.getImagePath())
                    .partyId(p.getId())
                    .title(p.getTitle())
                    .partyPeople(p.getPartyPeople())
                    .location(p.getLocation())
                    .partyDate(p.getPartyDate())
                    .likeCnt(likeCnt)
                    .viewCnt(viewCnt)
                    .commentCnt(commentsCnt)
                    .writer(nickname)
                    .deadline(p.getDeadLine())
                    .build();
            findParties.add(partyListResponseDto);
        }

        result.put("findParties",findParties);
        result.put("totalPages", partyList.getTotalPages());

        //responseDto 작성
        responseDto.setMessage("소모임 목록 리턴");
        responseDto.setData(result);
        responseDto.setStatus_code(200);

        return responseDto;
    }
    public ResponseDto listMyParty(String access_token) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        List<Party> myPartyList = partyRepository.findMyPartyList(user_id);
        List<PartyListResponseDto> findParties = new ArrayList<>();
        for (Party party : myPartyList) {
            PartyListResponseDto partyListResponseDto = PartyListResponseDto.builder()
                    .imagePath(party.getImagePath())
                    .partyPeople(party.getPartyPeople())
                    .partyId(party.getId())
                    .partyDate(party.getPartyDate())
                    .writer(party.getMember().getProfile().getNickname())
                    .title(party.getTitle())
                    .location(party.getLocation())
                    .likeCnt(party.getPartyInfo().getLikeCnt())
                    .viewCnt(party.getPartyInfo().getViewCnt())
                    .commentCnt(party.getPartyComments().size())
                    .deadline(party.getDeadLine())
                    .build();
            findParties.add(partyListResponseDto);
        }
        result.put("findParties",findParties);
        responseDto.setMessage("내가 만든 소모임 리스트");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    public ResponseDto createParty(String access_token, PartyCreateDto partyCreateDto) {
        Map<String, Object> result = new HashMap<>();
        ResponseDto responseDto = new ResponseDto();

        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);

        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        //DB
        Optional<Member> findMember = memberRepository.findById(user_id);
        Optional<Movie> findMovie = movieRepository.findById(partyCreateDto.getMovieId());
        PartyInfo partyInfo = new PartyInfo();
        Party party = Party.createParty(partyCreateDto, findMember.get(), findMovie.get(),partyInfo);
        partyRepository.save(party);

        //responseDto 작성
        result.put("partyId",party.getId());
        responseDto.setMessage("소모임 작성 완료");
        responseDto.setData(result);
        responseDto.setStatus_code(200);

        return responseDto;
    }
    @Transactional
    public ResponseDto modifyParty(String access_token,PartyModifyDto partyModifyDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Party party = partyRepository.findById(partyModifyDto.getPartyId()).get();
        if(user_id.equals(party.getMember().getId())) {
            party.modifyParty(partyModifyDto);
        }
        else{
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 소모임 주최자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("partyId",party.getId());
        responseDto.setMessage("소모임 수정 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    @Transactional
    public ResponseDto createComment(String access_token, PartyCommentCreateDto partyCommentCreateDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        Long user_id = HttpUtil.requestParingToken(access_token);

        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Member> findMember = memberRepository.findById(user_id);
        Optional<Party> findParty = partyRepository.findById(partyCommentCreateDto.getPartyId());

        PartyComment partyComment = PartyComment.createPartyComment(partyCommentCreateDto, findParty.get(), findMember.get());
        partyCommentRepository.save(partyComment);
        Long commentId = partyComment.getId();
        // 원댓글이면 groupId 를 본인 pk 로 저장
        if (partyComment.getGroupId().equals(0L)){
            partyComment.setGroupId(commentId);
        }
        //responseDto 작성
        result.put("commentId",commentId);
        responseDto.setMessage("댓글 작성 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    public ResponseDto createJoin(String access_token, PartyJoinCreateDto partyJoinCreateDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        Long user_id = HttpUtil.requestParingToken(access_token);

        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Optional<Member> findMember = memberRepository.findById(user_id);
        Optional<Party> findParty = partyRepository.findById(partyJoinCreateDto.getPartyId());

        PartyJoin partyJoin = PartyJoin.createPartyJoin(partyJoinCreateDto,findMember.get(),findParty.get());
        partyJoinRepository.save(partyJoin);
        //responseDto 작성
        result.put("joinId",partyJoin.getId());
        responseDto.setMessage("참가신청 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    @Transactional
    public ResponseDto updatePartyStatus(String access_token, Long partyId, int status) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        Long user_id = HttpUtil.requestParingToken(access_token);

        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        Party party = partyRepository.findById(partyId).get();
        if(user_id.equals(party.getMember().getId())) {
            if (status == 1) {
                party.setPartyStatus(PartyStatus.모집완료);
            } else if (status == 2) {
                party.setPartyStatus(PartyStatus.기간만료);
            } else if (status == 3) {
                party.setPartyStatus(PartyStatus.삭제);
            }
        }
        else {
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 소모임 주최자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("partyStatus",party.getPartyStatus());
        responseDto.setMessage("소모임 상태 변경 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);

        return responseDto;
    }
    @Transactional
    public ResponseDto updateJoinStatus(String access_token, Long joinId, int status) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        PartyJoin partyJoin = partyJoinRepository.findById(joinId).get();
        if(user_id.equals(partyJoin.getParty().getMember().getId())) {
            if (status == 1) {
                partyJoin.setPartyJoinStatus(PartyJoinStatus.승인);
            } else if (status == 2) {
                partyJoin.setPartyJoinStatus(PartyJoinStatus.거절);
            } else if (status == 3) {
                partyJoin.setPartyJoinStatus(PartyJoinStatus.취소);
            }
        }
        else {
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 소모임 주최자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("partyJoinStatus",partyJoin.getPartyJoinStatus());
        responseDto.setMessage("참가 신청 상태 변경 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    @Transactional
    public ResponseDto updateComment(String access_token, PartyCommentUpdateDto partyCommentUpdateDto) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }

        Long commentId = partyCommentUpdateDto.getCommentId();
        Optional<PartyComment> findComment = partyCommentRepository.findById(commentId);
        if(!findComment.isPresent()){
            responseDto.setStatus_code(400);
            responseDto.setMessage("수정할 댓글을 찾을 수 없습니다.");
            return responseDto;
        }
        PartyComment partyComment = findComment.get();
        Long id = partyComment.getMember().getId();
        log.info("현재 사용자 user_id : "+user_id);
        log.info("해당 댓글 작성자 id : "+id);
        if(user_id.equals(id)){
            partyComment.setContent(partyCommentUpdateDto.getContent());
            partyComment.setShowPublic(partyCommentUpdateDto.isShowPublic());
            partyComment.setCommentWriteTime(LocalDateTime.now());
        }
        else{
            responseDto.setStatus_code(400);
            responseDto.setMessage("해당 댓글 작성자가 아닙니다.");
            return responseDto;
        }
        //responseDto 작성
        result.put("partyCommentId", commentId);
        responseDto.setMessage("댓글 수정 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
    @Transactional
    public ResponseDto statusComment(String access_token, Long commentId, int statusCode) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        List<PartyComment> changeCommentList = partyCommentRepository.getChangeCommentList(commentId);
        if(changeCommentList.size() == 0){
            responseDto.setStatus_code(400);
            responseDto.setMessage("수정할 댓글을 찾을 수 없습니다.");
            return responseDto;
        }
        else{
            for (PartyComment partyComment : changeCommentList) {
                if(user_id.equals(partyComment.getMember().getId())) {
                    switch (statusCode) {
                        case 1:
                            partyComment.normalize();
                            break;
                        case 2:
                            partyComment.banned();
                            break;
                        case 3:
                            partyComment.deleted();
                            break;
                    }
                }
            }
        }

        //responseDto 작성
        result.put("partyCommentStatus", statusCode);
        responseDto.setMessage("댓글 상태 변경 성공");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }

    public ResponseDto listPartyJoin(String access_token) {
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> result = new HashMap<>();
        // token parsing 요청
        Long user_id = HttpUtil.requestParingToken(access_token);
        if(user_id.equals(0L)){
            responseDto.setStatus_code(400);
            responseDto.setMessage("회원 정보가 없습니다.");
            return responseDto;
        }
        List<PartyJoin> myJoinList = partyJoinRepository.findMyJoinList(user_id);
        List<PartyJoinListDto> partyJoinListDtos = new ArrayList<>();
        PartyJoinListDto partyJoinListDto;
        Party party;
        for (PartyJoin partyJoin : myJoinList) {
            party = partyJoin.getParty();
            partyJoinListDto = new PartyJoinListDto().builder()
                    .title(party.getTitle())
                    .location(party.getLocation())
                    .partyDate(party.getPartyDate())
                    .joinDate(partyJoin.getJoinDate())
                    .partyJoinStatus(partyJoin.getPartyJoinStatus())
                    .imagePath(party.getImagePath())
                    .partyId(party.getId())
                    .build();
            partyJoinListDtos.add(partyJoinListDto);
        }
        result.put("myPartyJoinList", partyJoinListDtos);
        responseDto.setMessage("참가 신청 리스트");
        responseDto.setData(result);
        responseDto.setStatus_code(200);
        return responseDto;
    }
}
