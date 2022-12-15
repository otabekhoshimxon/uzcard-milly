package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.ClientEntity;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity,String> {


    Optional<ClientEntity> getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);

    Optional<ClientEntity> getClientEntityById (String id);

    @Modifying
    @Transactional
    @Query(value = "update client  set status='ACTIVE' where  phoneNumber=?1 " ,nativeQuery = true)
    void   setActiveClient(String phone);
}
