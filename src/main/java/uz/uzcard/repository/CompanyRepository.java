package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.CompanyEntity;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity,String> {


    boolean existsByUsername(String username);

    Optional<CompanyEntity> getByUsername(String username);

    @Modifying
    @Transactional
    @Query("update CompanyEntity set username=?2 ,password=?3 where id=?1")
    void changeUsernameAndPasswordById(String id, String username, String password);
}
