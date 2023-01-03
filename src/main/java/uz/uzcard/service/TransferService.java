package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.uzcard.entity.TransferEntity;
import uz.uzcard.repository.TransferRepository;

import java.util.Optional;

@Service
public class TransferService {


    @Autowired
    private TransferRepository transferRepository;


    public TransferEntity getById(String transferId) {


        Optional<TransferEntity> byId = transferRepository.findById(transferId);
        return byId.orElse(null);


    }
}
