package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.ClientEntity;

import java.util.List;
import java.util.Optional;
@RedisHash
public interface ClientRepository extends JpaRepository<ClientEntity,String> {





    Optional<ClientEntity> getClientEntityById (String id);

    @Modifying
    @Transactional
    @Query(value = "update ClientEntity  set status='ACTIVE' where  phoneNumber=?1 " )
    void setActiveClient(String phone);

    Optional<ClientEntity> getByPhone(String phone);

    boolean existsByPhone(String phone);
}
