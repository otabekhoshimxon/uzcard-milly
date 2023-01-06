package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.config.CustomUserDetails;
import uz.uzcard.dto.TransferResponceDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.dto.transfer.TransferCreateDTO;
import uz.uzcard.dto.transfer.TransferStatusDTO;
import uz.uzcard.entity.CardEntity;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.entity.TransferEntity;
import uz.uzcard.enums.TransactionType;
import uz.uzcard.enums.TransferStatus;
import uz.uzcard.repository.TransferRepository;
import uz.uzcard.util.CurrentUserUtil;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class TransferService {
    @Value("${uz.card.id}")
    private String uzCardId;

    @Value("${uz.card.service.percentage}")
    private Double uzCardServicePercentage;
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    @Lazy
    private TransactionService transactionService;
    @Autowired
    private CardService cardService;
    @Autowired
    private CompanyService companyService;


    public TransferEntity getById(String transferId) {


        Optional<TransferEntity> byId = transferRepository.findById(transferId);
        return byId.orElse(null);


    }

    public ResponseEntity create(TransferCreateDTO transferCreate) {


        CardEntity from = cardService.getCardById(transferCreate.getFromCardId());
        CardEntity to = cardService.getCardById(transferCreate.getToCardId());


        if (from == null) {

            return ResponceDTO.sendBadRequestResponce(-1, "From card not found");
        }
        if (to == null) {
            return ResponceDTO.sendBadRequestResponce(-1, "To card not found");
        }
        CustomUserDetails currentUser = currentUserUtil.getCurrentUser();
        CompanyEntity company = companyService.getById(currentUser.getId());
        double service_percentage = uzCardServicePercentage + company.getServicePersentage(); // 0.7
        double service_amount = (transferCreate.getAmount() /100)* service_percentage; // 70
        double total_amount = transferCreate.getAmount() + service_amount; // 10,070

        if (from.getBalance() < total_amount) {
            return ResponceDTO.sendBadRequestResponce(-1, "Not enough money");
        }

        TransferEntity transfer = new TransferEntity();
        transfer.setFromCardId(from.getId());
        transfer.setToCardId(to.getId());
        transfer.setTotalAmount(total_amount);
        transfer.setAmount(transferCreate.getAmount());
        transfer.setStatus(TransferStatus.IN_PROGRESS);
        transfer.setService_amount(service_amount);
        transfer.setService_percentage(company.getServicePersentage());
        transfer.setCompanyId(company.getId());
        transferRepository.save(transfer);


        TransferResponceDTO responceDTO = new TransferResponceDTO();
        responceDTO.setId(transfer.getId());
        responceDTO.setCreatedDate(transfer.getCreatedDate());
        responceDTO.setTotalAmount(transfer.getTotalAmount());
        responceDTO.setServiceAmount(transfer.getService_amount());
        responceDTO.setStatus(transfer.getStatus());
        responceDTO.setCompanyId(company.getId());
        responceDTO.setAmount(transfer.getAmount());
        responceDTO.setToCard(transfer.getToCardId());
        responceDTO.setFromCard(transfer.getFromCardId());
        responceDTO.setServicePersentage(service_percentage);
        return ResponseEntity.ok(responceDTO);

    }

    public ResponseEntity changeStatus(String id, @Valid TransferStatusDTO transferStatus) {


        if (!(transferStatus.equals(TransferStatus.SUCCESS.name()) || transferStatus.equals(TransferStatus.FAILED.name()))) {
            return ResponceDTO.sendBadRequestResponce(-1, "Invalid transfer status");
        }

        Optional<TransferEntity> byId = transferRepository.findById(id);
        if(byId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1, "Transfer not found");

        }

        TransferEntity transfer = byId.get();
        transfer.setStatus(TransferStatus.valueOf(transferStatus.getStatus()));
        transferRepository.save(transfer);
        return ResponceDTO.sendOkResponce(1, "Transfer successfully updated status");


    }

    public ResponseEntity cancel(String id) {
        Optional<TransferEntity> optional =
                transferRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponceDTO.sendBadRequestResponce(-1, "Transfer not found");
        }

        TransferEntity entity = optional.get();

        if (entity.getStatus().equals(TransferStatus.SUCCESS)) {

                   return ResponceDTO.sendBadRequestResponce(-1, "Not allowed to cancel a successed transfer");

        }

        if (entity.getStatus().equals(TransferStatus.CANCELED)) {
            return ResponceDTO.sendBadRequestResponce(-1,         "Already canceled a transfer");

        }


        return ResponceDTO.sendOkResponce(1, "Transfer successfully canceled");
    }

    public ResponseEntity reverse(String id) {



        return null;
    }


    public ResponseEntity finish(String id) {


        Optional<TransferEntity> optional =transferRepository.findById(id);
        if(optional.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1, "Transfer not found");
        }

        TransferEntity transfer = optional.get();
        CompanyEntity company = companyService.getById(transfer.getCompanyId());
        CardEntity from = cardService.getCardById(transfer.getFromCardId());

        double service_percentage = uzCardServicePercentage + company.getServicePersentage(); // 0.7
        double uzcardServiceAmount = (transfer.getAmount() /100)* service_percentage; // 70
        double companyServiceAmount = (transfer.getAmount() /100)* company.getServicePersentage(); // 70
        double total_amount = transfer.getAmount() + uzcardServiceAmount+companyServiceAmount; // 10,070

        if (from.getBalance() < total_amount) {
            return ResponceDTO.sendBadRequestResponce(-1, "Not enough money");
        }
        transactionService.create(transfer.getFromCard().getId(),transfer.getTotalAmount(),transfer.getId(), TransactionType.CREDIT);
        transactionService.create(transfer.getToCard().getId(),  transfer.getAmount(),     transfer.getId(), TransactionType.DEBIT);
        transactionService.create(transfer.getCompanyId(),       companyServiceAmount,     transfer.getId(), TransactionType.DEBIT);
        transactionService.create(uzCardId,                      uzcardServiceAmount,      transfer.getId(), TransactionType.DEBIT);

        cardService.changeBalance(transfer.getFromCardId(),transfer.getTotalAmount(),TransactionType.CREDIT);
        cardService.changeBalance(transfer.getToCardId(),  transfer.getAmount(),     TransactionType.DEBIT);
        cardService.changeBalance(transfer.getCompanyId(), companyServiceAmount,     TransactionType.DEBIT);
        cardService.changeBalance(uzCardId,                uzcardServiceAmount,      TransactionType.DEBIT);

        transferRepository.changeStatus(TransferStatus.SUCCESS,transfer.getId());

        return ResponceDTO.sendOkResponce(1, "Transfer successfully finished");

    }
}
