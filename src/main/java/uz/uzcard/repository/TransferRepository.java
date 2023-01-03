package uz.uzcard.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import uz.uzcard.entity.TransferEntity;

public interface TransferRepository extends PagingAndSortingRepository<TransferEntity ,String> {
}
