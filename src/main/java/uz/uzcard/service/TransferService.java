package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.config.CustomUserDetails;
import uz.uzcard.dto.TransferResponceDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.dto.transfer.TransferCreateDTO;
import uz.uzcard.entity.CardEntity;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.entity.TransferEntity;
import uz.uzcard.enums.TransferStatus;
import uz.uzcard.repository.TransferRepository;
import uz.uzcard.util.CurrentUserUtil;

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
        transfer.setStatus(TransferStatus.SUCCESS);
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
}
