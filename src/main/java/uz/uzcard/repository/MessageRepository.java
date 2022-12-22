package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uz.uzcard.entity.MessageEntity;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity,String> {




    @Query(value = "select  count(*) from message where phone=?1 ",nativeQuery = true)
    int getMessageCount(String phone);


    @Query(value = "from MessageEntity where phone=?1 order by id asc ")
    Optional<MessageEntity> getDistinctFirstByCodeOrderByIdIdAsc(String phone);
}
