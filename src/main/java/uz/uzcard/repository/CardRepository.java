package uz.uzcard.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.CardEntity;

import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<CardEntity, String> {

    @Modifying
    @Transactional
    @Query("update CardEntity set status='ACTIVE' where phone=?1")
    void activateCardByPhone(String phoneNumber);

    boolean existsByPhone(String phoneNumber);

    @Query(value = "select * from card  cd" +
            " inner join client cl on cl.id=cd.client_id " +
            " inner join company cm on cm.id=cl.company_id " +
            " where cd.id=:cardId and cm.id=:comId ",nativeQuery = true)
    Optional<CardEntity> getCardCreatorById(@Param("cardId") String id, @Param("comId")String currentUserId);
}
