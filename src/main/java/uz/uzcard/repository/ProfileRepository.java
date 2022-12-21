package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisHash;
import uz.uzcard.entity.ProfileEntity;

import java.util.List;
import java.util.Optional;
@RedisHash
public interface ProfileRepository extends JpaRepository<ProfileEntity,String> {




    Optional<ProfileEntity> getByPhone(String phone);

    boolean existsByPhone(String phone);
}
