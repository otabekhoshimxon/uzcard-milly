package uz.uzcard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uz.uzcard.config.CustomUserDetails;
import uz.uzcard.dto.AssignPhoneDTO;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.dto.card.*;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CardEntity;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.enums.TransactionType;
import uz.uzcard.repository.CardFilterRepository;
import uz.uzcard.repository.CardRepository;
import uz.uzcard.util.CardNumberGenerator;
import uz.uzcard.util.CurrentUserUtil;

import java.util.*;

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
        card.setPassword(cardCreate.getPassword());
        card.setPrefix(company.getCardPrefix());
        card.setCompanyId(currentUser.getId());
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


        CardEntity card = byId.get();
        if (checkRole("BANK")){

            if (card.getStatus().equals(GeneralStatus.ACTIVE)){
                card.setStatus(GeneralStatus.BLOCK);
            }
            else {
                card.setStatus(GeneralStatus.ACTIVE);
            }
            cardRepository.save(card);


        }
        if (checkRole("PAYMENT")){
            if (card.getStatus().equals(GeneralStatus.ACTIVE)){
                card.setStatus(GeneralStatus.BLOCK);
            }

        }
        return ResponceDTO.sendOkResponce(1,"Successfully changed status card");
    }


    public boolean checkRole(String role){
        Collection<? extends GrantedAuthority> authorities = currentUserUtil.getCurrentUser().getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(role));
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

    public ResponseEntity getCardByCardId(String id) {

        if (!cardRepository.existsById(id)){
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found");
        }
        CustomUserDetails currentUser = currentUserUtil.getCurrentUser();

        if (checkRole("BANK")){

            Optional<CardEntity> cardCreatorById = cardRepository.getCardByIdAndCreatorId(id,currentUser.getId());
            if (cardCreatorById.isEmpty()){
                return ResponceDTO.sendBadRequestResponce(-1,"Card not by  found");
            }
            return ResponseEntity.ok(getCardDTO(cardCreatorById.get()));
        }

        return ResponseEntity.ok(getCardDTO(cardRepository.findById(id).get()));
    }


    public ResponseEntity getCardListByPhone(CardPhoneDTO phone) {


        if (!cardRepository.existsByPhone(phone.getPhone())){
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found ");

        }
        CustomUserDetails currentUser = currentUserUtil.getCurrentUser();

        if (checkRole("BANK")){

            List<CardEntity> cardListByPhoneAndCompanyId = cardRepository.getCardListByPhoneAndCompanyId(phone.getPhone(), currentUser.getId());
            List<CardDTO> cardDTOS=new ArrayList<>();
            for (CardEntity card : cardListByPhoneAndCompanyId) {
                cardDTOS.add(getCardDTO(card));
            }

            return ResponseEntity.ok(cardDTOS);


        }
        List<CardEntity> cardListByPhone = cardRepository.getCardListByPhone(phone.getPhone());

        List<CardDTO> cardDTOS=new ArrayList<>();
        for (CardEntity card : cardListByPhone) {
           cardDTOS.add(getCardDTO(card));
        }

        return ResponseEntity.ok(cardDTOS);
    }

    public ResponseEntity getCardListByClientId(String id) {

        if (checkRole("BANK")){


            List<CardEntity> cardListByClientIdAndCompanyId = cardRepository.getCardListByClientIdAndCompanyId(id, currentUserUtil.getCurrentUser().getId());


            if (cardListByClientIdAndCompanyId.isEmpty()){
                return ResponceDTO.sendBadRequestResponce(-1,"Card not found");
            }

            return ResponseEntity.ok(dtoList(cardListByClientIdAndCompanyId));

        }

        List<CardEntity> cardListByClientId = cardRepository.getByClientId(id);
        if (cardListByClientId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found");
        }


        return ResponseEntity.ok(dtoList(cardListByClientId));

    }


    public ResponseEntity getCardByNumber(CardNumberDTO cardNumber) {


        if (checkRole("BANK")) {

            Optional<CardEntity> cardByNumberAndCompanyId = cardRepository.getCardByNumberAndCompanyId(cardNumber.getCardNumber(), currentUserUtil.getCurrentUser().getId());
            if (cardByNumberAndCompanyId.isEmpty()) {
                return ResponceDTO.sendBadRequestResponce(-1, "Card not found");
            }

            CardEntity card = cardByNumberAndCompanyId.get();
            return ResponseEntity.ok(getCardDTO(card));


        }

        Optional<CardEntity> cardByNumber = cardRepository.getCardByNumber(cardNumber.getCardNumber());
        if (cardByNumber.isEmpty()) {
            return ResponceDTO.sendBadRequestResponce(-1, "Card not found");
        }

        CardEntity card = cardByNumber.get();
        return ResponseEntity.ok(getCardDTO(card));

    }


///////////////////////////////////////////////////


    public CardDTO getCardDTO(CardEntity card){


        CardDTO dto=new CardDTO();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setBalance(card.getBalance());
        dto.setClientId(card.getClientId());
        dto.setCreatedDate(card.getCreatedDate());
        dto.setExpiredDate(card.getExpiredDate());
        dto.setPhone(card.getPhone());
        dto.setStatus(card.getStatus());
        dto.setVisible(card.getVisible());
        return dto;
    }


    public List<CardDTO> dtoList(List<CardEntity> entityList) {
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (CardEntity card : entityList) {

            cardDTOS.add(getCardDTO(card));
        }
        return cardDTOS;

    }

    public ResponseEntity getCardBalance(CardNumberDTO cardNumber) {



        if (!cardRepository.existsByNumber(cardNumber.getCardNumber())){
            return ResponceDTO.sendBadRequestResponce(-1,"Card not found");
        }

        if (checkRole("BANK"))
        {
            Long cardBalanceByNumberAndCompanyId = cardRepository.getCardBalanceByNumberAndCompanyId(cardNumber.getCardNumber(), currentUserUtil.getCurrentUser().getId());

            return ResponseEntity.ok(cardBalanceByNumberAndCompanyId);



        }

        Long balanceByNumber = cardRepository.getBalanceByNumber(cardNumber.getCardNumber());
        return  ResponseEntity.ok(balanceByNumber);

    }

    public ResponseEntity filter(CardFilterDTO filter) {
        @SuppressWarnings("unchecked")
        List cardInfo = new CardFilterRepository().getCardInfo(filter);
        return ResponseEntity.ok(cardInfo);




    }

    public CardEntity getCardById(String cardId) {
        Optional<CardEntity> byId = cardRepository.findById(cardId);
        CardEntity card = byId.orElse(null);
        return card;

    }

    public void changeBalance(String cardId, Double amount, TransactionType type) {

        if (type.equals(TransactionType.CREDIT)){
            cardRepository.minusBalance(cardId,amount);
        }
        else if (type.equals(TransactionType.DEBIT)){

            cardRepository.plusBalance(cardId,amount);

        }
    }
}

