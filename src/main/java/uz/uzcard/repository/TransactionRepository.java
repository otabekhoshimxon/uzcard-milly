package uz.uzcard.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import uz.uzcard.dto.card.CardPhoneDTO;
import uz.uzcard.entity.TransactionEntity;
import uz.uzcard.enums.TransactionType;

import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<TransactionEntity,String> {


    @Query(value = "from TransactionEntity where cardId=?1")
    List<TransactionEntity> findByCardId(String id, Pageable pageable);

    @Query(nativeQuery = true ,value = "select count(*) from  transaction where card_id=:cardId")
    Integer countByCardId(@Param("cardId") String idcardId);

    @Query(nativeQuery = true ,value = "select count(*) from  transaction tr " +
            " inner join card cd  on cd.id=tr.card_id " +
            "where cd.number=:cardNumber ")
    Integer countByCardNumber(String cardNumber);
    @Query(value = "from TransactionEntity t where t.card.number=?1")
    List<TransactionEntity> findByCardNumber(@Param("cardNumber") String cardNumber, Pageable pageable);



    @Query("from  TransactionEntity t where t.card.company.creatorId=:pId")
    List<TransactionEntity> findByProfileId(@Param("pId") String id, Pageable pageable);


    @Query(nativeQuery = true ,value = "select count(*) from  transaction tr " +
            " inner join card cd on cd.id=tr.card_id " +
            " where cd.phone=:phoneNumber ")
    int countByPhone(@Param("phoneNumber") String phone);


    @Query(nativeQuery = true ,value = "select * from  transaction tr " +
            " inner join card cd on cd.id=tr.card_id " +
            " where cd.phone=:phoneNumber ")
    List<TransactionEntity> findByPhone(@Param("phoneNumber") String phone, Pageable pageable);

    @Query("from TransactionEntity where type=?1 and cardId=?2 ")
    List<TransactionEntity> getByTransactionTypeAndCardId(TransactionType type, String cardId, Pageable pageable);

    @Query(nativeQuery = true,value = "select sum(amount) from  transaction tr  where   tr.created_date < (now() + '1 months') and  tr.card_id=:cardId")
    Double getDebitAndCreditbyCardIdAmountByMonthly(@PathVariable("cardId") String cardId);
}
