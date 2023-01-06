package uz.uzcard.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import uz.uzcard.entity.TransferEntity;
import uz.uzcard.enums.TransferStatus;

public interface TransferRepository extends PagingAndSortingRepository<TransferEntity ,String> {
    @Modifying
    @Transactional
    @Query("update TransferEntity  set status=?1 where id=?2")
    void changeStatus(TransferStatus success, String id);
}
