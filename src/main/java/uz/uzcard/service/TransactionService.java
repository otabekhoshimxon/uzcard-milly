package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.TransactionCreateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CardEntity;
import uz.uzcard.entity.TransactionEntity;
import uz.uzcard.entity.TransferEntity;
import uz.uzcard.enums.TransactionType;
import uz.uzcard.repository.TransactionRepository;

import java.util.Arrays;

@Service
public class TransactionService {


    @Autowired
    private CardService cardService;

    @Autowired
    private TransferService transferService;


    @Autowired
    private TransactionRepository transactionRepository;


    public ResponseEntity create(TransactionCreateDTO transactionCreate) {

        CardEntity cardById = cardService.getCardById(transactionCreate.getCardId());

        if (cardById == null) {
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found ");
        }

        TransferEntity byId = transferService.getById(transactionCreate.getTransferId());

        if (byId == null) {

            return ResponceDTO.sendBadRequestResponce(-1,"Transfer not found ");

        }


        if (!(TransactionType.valueOf(transactionCreate.getType().toUpperCase())==TransactionType.CREDIT
            || TransactionType.valueOf(transactionCreate.getType().toUpperCase())==TransactionType.DEBIT)){

            return ResponceDTO.sendBadRequestResponce(-1,"Type is not correct");

        }

        TransactionEntity entity=new TransactionEntity();
        entity.setAmount(transactionCreate.getAmount());
        entity.setCardId(cardById.getId());
        entity.setType(TransactionType.valueOf(transactionCreate.getType().toUpperCase()));
        entity.setTransferId(transactionCreate.getTransferId());

        transactionRepository.save(entity);


        return ResponceDTO.sendOkResponce(1,"Successfully created transaction");


    }
}
