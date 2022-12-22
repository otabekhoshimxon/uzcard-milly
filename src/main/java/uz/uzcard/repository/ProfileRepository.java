package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import uz.uzcard.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity,String> {




    Optional<ProfileEntity> getByPhone(String phone);

    boolean existsByPhone(String phone);
}
