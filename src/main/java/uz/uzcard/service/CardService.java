package uz.uzcard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uz.uzcard.config.CustomUserDetails;
import uz.uzcard.dto.AssignPhoneDTO;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.dto.card.CardCreateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CardEntity;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.repository.CardRepository;
import uz.uzcard.util.CardNumberGenerator;
import uz.uzcard.util.CurrentUserUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private ClientService clientService;
    @Autowired
    private CompanyService companyService;
  @Autowired
    private MessageService messageService;

    @Autowired
    private CardRepository cardRepository;


    public ResponseEntity createCard(CardCreateDTO cardCreate) {
        CustomUserDetails currentUser = currentUserUtil.getCurrentUser();
        CompanyEntity company = companyService.getById(currentUser.getId());
        ClientEntity client = clientService.getById(cardCreate.getClientId());
        if (Objects.isNull(client)) {
            return ResponceDTO.sendBadRequestResponce(-1, "Client not found");
        }

        if (Objects.isNull(company)) {
            return ResponceDTO.sendBadRequestResponce(-1, "Client not found");
        }

        CardEntity card = new CardEntity();
        card.setBalance(cardCreate.getBalance());
        card.setPhone(client.getPhone());
        card.setClientId(client.getId());
        card.setPrefix(company.getCardPrefix());
        card.setNumber(new CardNumberGenerator().generate(company.getCardPrefix(), 16));
        card.setStatus(GeneralStatus.NOT_ACTIVE);
        cardRepository.save(card);
        messageService.sendActivateCardCode(card.getPhone());
        return ResponceDTO.sendOkResponce(card.getPhone(), 1, "Activate code sent to number");
    }

    public ResponseEntity activateCard(VerificationDTO verification) {


        if ( messageService.getMessageCount(verification.getPhoneNumber())>=3){
            return ResponceDTO.sendOkResponce(-1,"xabarlar soni 3 tadan oshiq");
        }

        cardRepository.activateCardByPhone(verification.getPhoneNumber());
        return ResponceDTO.sendOkResponce(1,"Card activated");

    }

    public ResponseEntity changeStatusCard(String id) {


        Optional<CardEntity> byId = cardRepository.findById(id);
        if (byId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found");
        }

        Collection<? extends GrantedAuthority> authorities = currentUserUtil.getCurrentUser().getAuthorities();

        CardEntity card = byId.get();
        if (authorities.contains(new SimpleGrantedAuthority("BANK"))){

            if (card.getStatus().equals(GeneralStatus.ACTIVE)){
                card.setStatus(GeneralStatus.BLOCK);
            }
            else {
                card.setStatus(GeneralStatus.ACTIVE);
            }
            cardRepository.save(card);


        }
        if (authorities.contains(new SimpleGrantedAuthority("PAYMENT"))){
            if (card.getStatus().equals(GeneralStatus.ACTIVE)){
                card.setStatus(GeneralStatus.BLOCK);
            }

        }
        return ResponceDTO.sendOkResponce(1,"Successfully changed status card");
    }

    public ResponseEntity assignPhone(AssignPhoneDTO assignPhone) {
        Optional<CardEntity> byId = cardRepository.findById(assignPhone.getCardId());
        if (byId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found");

        }
        if (cardRepository.existsByPhone(assignPhone.getPhoneNumber())){
            return ResponceDTO.sendBadRequestResponce(-1,"Phone number already registered");

        }
        CardEntity card = byId.get();
        card.setPhone(assignPhone.getPhoneNumber());
        cardRepository.save(card);

        return ResponceDTO.sendOkResponce(card.getPhone(),1,"Assigned phone number");

    }

}
