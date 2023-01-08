package uz.uzcard.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uz.uzcard.entity.TransactionEntity;

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
}
