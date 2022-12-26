package uz.uzcard.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.CardEntity;

public interface CardRepository extends PagingAndSortingRepository<CardEntity, String> {

    @Modifying
    @Transactional
    @Query("update CardEntity set status='ACTIVE' where phone=?1")
    void activateCardByPhone(String phoneNumber);
}
