package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzcard.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity,String> {



}
