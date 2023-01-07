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
}
