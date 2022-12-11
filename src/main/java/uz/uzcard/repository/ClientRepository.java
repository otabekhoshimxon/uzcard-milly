package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzcard.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity,String> {



}
