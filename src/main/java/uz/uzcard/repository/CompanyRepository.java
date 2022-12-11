package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzcard.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity,String> {



}
