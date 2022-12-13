package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzcard.entity.CompanyEntity;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity,String> {


    boolean existsByUsername(String username);

    Optional<CompanyEntity> getByUsername(String username);
}
