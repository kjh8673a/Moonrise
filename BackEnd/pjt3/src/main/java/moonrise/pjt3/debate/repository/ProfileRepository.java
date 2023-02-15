package moonrise.pjt3.debate.repository;

import io.lettuce.core.dynamic.annotation.Param;
import moonrise.pjt3.debate.entity.Message;
import moonrise.pjt3.member.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    @Query(value = "select p.profile_image_path from Profile p where p.nickname like :nickName")
    String findImagePath(@Param("nickName") String nickName);
}
