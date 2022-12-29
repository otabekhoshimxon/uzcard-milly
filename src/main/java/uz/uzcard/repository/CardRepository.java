package uz.uzcard.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.dto.card.CardNumberDTO;
import uz.uzcard.entity.CardEntity;

import java.util.List;
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
    Optional<CardEntity> getCardByIdAndCreatorId(@Param("cardId") String id, @Param("comId")String currentUserId);


    @Query(value = "select * from card cd " +
            " inner join client cl on cl.id =cd.client_id" +
            " where cl.company_id=:comId and cd.phone=:ph ",nativeQuery = true)
    List<CardEntity> getCardListByPhoneAndCompanyId( @Param("ph")String phone, @Param("comId") String id);



    @Query(value = " select  * from card where phone=:ph and visible=true ",nativeQuery = true)
    List<CardEntity> getCardListByPhone( @Param("ph")String phone);


    @Query(value = " select  * from  card cd " +
            "  where cd.client_id=:clId and cd.company_id=:comId",nativeQuery = true)
    List<CardEntity> getCardListByClientIdAndCompanyId(@Param("clId") String id, @Param("comId") String id1);


    @Query(value = " select  * from card where client_id=:clId",nativeQuery = true)
    List<CardEntity> getByClientId(@Param("clId") String id);

    
    
    @Query(value = " select  * from card where number=:cn and company_id=:comId" ,nativeQuery = true)
    Optional<CardEntity> getCardByNumberAndCompanyId(@Param("cn") String cardNumber, @Param("comId") String id);


    Optional<CardEntity> getCardByNumber(String cardNumber);
}
