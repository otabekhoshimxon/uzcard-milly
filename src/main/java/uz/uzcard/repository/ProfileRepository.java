package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,String> {


    boolean existsByUsername(String username);

    Optional<ProfileEntity> getByUsername(String username);
}
