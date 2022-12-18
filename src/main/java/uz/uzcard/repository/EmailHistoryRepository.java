package uz.uzcard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.uzcard.entity.EmailHistoryEntity;

public interface EmailHistoryRepository extends PagingAndSortingRepository<EmailHistoryEntity,String> {
    Long countByEmail(String email);


}
