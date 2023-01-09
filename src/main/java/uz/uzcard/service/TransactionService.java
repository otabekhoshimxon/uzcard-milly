package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.CardInfoDTO;
import uz.uzcard.dto.ClientShortInfoDTO;
import uz.uzcard.dto.TransactionInfoDTO;
import uz.uzcard.dto.card.CardPhoneDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CardEntity;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.entity.TransactionEntity;
import uz.uzcard.entity.TransferEntity;
import uz.uzcard.enums.TransactionStatus;
import uz.uzcard.enums.TransactionType;
import uz.uzcard.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;




    public void create(String cardId, Double totalAmount, String transferId, TransactionType type) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setType(type);
        transaction.setTransferId(transferId);
        transaction.setCardId(cardId);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setAmount(totalAmount);
        transactionRepository.save(transaction);
    }


    public ResponseEntity getByCardId(String cardId, Integer page, Integer size) {
        if (transactionRepository.countByCardId(cardId)==0){
            return ResponceDTO.sendBadRequestResponce(-1, "Card does not exist");
        }
        Pageable pageable= PageRequest.of(page,size);
        List<TransactionEntity> byId = getTransactionListByCardId(cardId, pageable);
        if (byId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1, "Transactions does not exist");
        }
        PageImpl page1=new PageImpl(getInfoList(byId),pageable,byId.size());
        return ResponseEntity.ok(page1);
    }


    public ResponseEntity getByCardNumber(String cardNumber, Integer page, Integer size) {
        if (transactionRepository.countByCardNumber(cardNumber)==0){
            return ResponceDTO.sendBadRequestResponce(-1, "Transactions does not exist");
        }
        Pageable pageable= PageRequest.of(page,size);
        List<TransactionEntity> transactionListByCardNumber = getTransactionListByCardNumber(cardNumber, pageable);

        PageImpl page1=new PageImpl(getInfoList(transactionListByCardNumber),pageable,transactionListByCardNumber.size());
        return ResponseEntity.ok(page1);

    }


    public ResponseEntity getByProfileId(String id, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);


        List<TransactionEntity> byProfileId = transactionRepository.findByProfileId(id,pageable);
        PageImpl page1=new PageImpl(getInfoList(byProfileId),pageable,byProfileId.size());
        return ResponseEntity.ok(page1);



    }

    public ResponseEntity getByPhone(CardPhoneDTO phone, Integer page, Integer size) {

        if(transactionRepository.countByPhone(phone.getPhone())==0){
            return ResponceDTO.sendBadRequestResponce(-1, "Transactions does not exist");

        }

        Pageable pageable= PageRequest.of(page,size);
        List<TransactionEntity> byPhone = transactionRepository.findByPhone(phone.getPhone(),pageable);
        PageImpl page1=new PageImpl(getInfoList(byPhone),pageable,byPhone.size());
        return ResponseEntity.ok(page1);



    }


    public ResponseEntity getCreditByCardId(String cardId, Integer page, Integer size) {


        if (transactionRepository.countByCardId(cardId)==0){
            return ResponceDTO.sendBadRequestResponce(-1, "Transactions does not exist");

        }
        Pageable pageable=PageRequest.of(page,size);
        List<TransactionEntity> byCardId = transactionRepository.getByTransactionTypeAndCardId(TransactionType.CREDIT,cardId,pageable);

        PageImpl page1=new PageImpl(getInfoList(byCardId),pageable,byCardId.size());
        return ResponseEntity.ok(page1);

    }









    public List<TransactionEntity> getTransactionListByCardNumber(String cardNumber,Pageable pageable){
        List<TransactionEntity> byCardNumber = transactionRepository.findByCardNumber(cardNumber, pageable);
        return byCardNumber;
    }
    public List<TransactionEntity> getTransactionListByCardId(String cardId,Pageable pageable){
        List<TransactionEntity> byId = transactionRepository.findByCardId(cardId, pageable);
        return byId;
    }




    public List<TransactionInfoDTO> getInfoList(List<TransactionEntity> list){

        List<TransactionInfoDTO> transactionInfoDTOS=new ArrayList<>();
        for (TransactionEntity entity : list) {
            transactionInfoDTOS.add(getInfoDTO(entity));
        }
        return transactionInfoDTOS;
    }


    public TransactionInfoDTO getInfoDTO(TransactionEntity transaction) {


        TransferEntity transfer = transaction.getTransfer();
        CardEntity toCard = transfer.getToCard();
        CardEntity fromCard = transfer.getFromCard();
        //////////////////////////////////////////
        ClientEntity toCardClient = toCard.getClient();
        ClientShortInfoDTO clientShortInfoDTO=new ClientShortInfoDTO();
        clientShortInfoDTO.setId(toCardClient.getId());
        clientShortInfoDTO.setSurname(toCardClient.getSurname());
        clientShortInfoDTO.setName(toCardClient.getName());
        CardInfoDTO toCardInfo = new CardInfoDTO();
        toCardInfo.setId(toCard.getId());
        toCardInfo.setNumber(toCard.getNumber());
        toCardInfo.setPhone(toCard.getPhone());
        toCardInfo.setClient(clientShortInfoDTO);

        //////////////////////////////////////////

        ClientEntity fromCardClient = fromCard.getClient();
        ClientShortInfoDTO fromCardClientShortInfo=new ClientShortInfoDTO();
        fromCardClientShortInfo.setId(fromCardClient.getId());
        fromCardClientShortInfo.setSurname(fromCardClient.getSurname());
        fromCardClientShortInfo.setName(fromCardClient.getName());
        CardInfoDTO fromCardInfo = new CardInfoDTO();
        fromCardInfo.setId(toCard.getId());
        fromCardInfo.setNumber(toCard.getNumber());
        fromCardInfo.setPhone(toCard.getPhone());
        fromCardInfo.setClient(clientShortInfoDTO);

        ///////////////////////////////////////////


        TransactionInfoDTO transactionInfoDTO = new TransactionInfoDTO();
        transactionInfoDTO.setId(transaction.getId());
        transactionInfoDTO.setFromCard(fromCardInfo);
        transactionInfoDTO.setToCard(toCardInfo);
        transactionInfoDTO.setAmount(transaction.getAmount());
        transactionInfoDTO.setStatus(transaction.getStatus());
        transactionInfoDTO.setType(transaction.getType());
        transactionInfoDTO.setCreatedDate(transaction.getCreatedDate());
        return transactionInfoDTO;
    }



}
