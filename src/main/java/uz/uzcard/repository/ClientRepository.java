package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity,String> {





    Optional<ClientEntity> getClientEntityById (String id);

    @Modifying
    @Transactional
    @Query(value = "update ClientEntity  set status='ACTIVE' where  phone=?1 " )
    void setActiveClient(String phone);

    Optional<ClientEntity> getByPhone(String phone);

    boolean existsByPhone(String phone);
}
