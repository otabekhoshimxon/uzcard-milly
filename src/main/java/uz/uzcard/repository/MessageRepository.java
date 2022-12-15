package uz.uzcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.uzcard.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity,String> {




    @Query(value = "select  count(*) from message where phone=?1 ",nativeQuery = true)
    int getMessageCount(String phone);


    @Query(value = "select code from message where phone=?1 order by id desc ",nativeQuery = true)
    String getLastMessageCodeByPhone(String phone);
}
