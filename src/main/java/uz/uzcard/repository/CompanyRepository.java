package uz.uzcard.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

@RedisHash
public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity,String> {



  
    @Modifying
    @Transactional
    @Query("update CompanyEntity set visible=false where id=?1")
    void setVisibleById(String id);

  

    Optional<CompanyEntity> getByPhone(String phone);

    boolean existsByPhone(String phone);

    @Modifying
    @Transactional
    @Query("update CompanyEntity set phone=?2 ,password=?3 where id=?1")
    void changePhoneAndPasswordById(String id, String phone, String md5Password);
}
