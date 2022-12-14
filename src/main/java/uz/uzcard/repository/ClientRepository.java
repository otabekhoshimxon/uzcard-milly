package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzcard.entity.ClientEntity;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity,String> {


    Optional<ClientEntity> getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);
}
