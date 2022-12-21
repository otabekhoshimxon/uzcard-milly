package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.dto.client.ClientCreateDTO;
import uz.uzcard.dto.client.ClientFilterDTO;
import uz.uzcard.dto.client.ClientUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.repository.ClientFilterRepository;
import uz.uzcard.repository.ClientRepository;
import uz.uzcard.util.CompanyUtil;

import java.util.Optional;

@Service
public class ClientService  {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientFilterRepository clientFilter;
    @Autowired
    private CompanyUtil companyUtil;
    @Autowired
    @Lazy
    private MessageService messageService;



    public boolean existsById(String id) {
        return clientRepository.existsById(id);
    }



    public ResponseEntity create(ClientCreateDTO create) {


        if (clientRepository.existsByPhone(create.getPhone())){
            return ResponceDTO.sendBadRequestResponce(-1,"Phone number already registered");
        }

        ClientEntity client=new ClientEntity(create);
        client.setStatus(GeneralStatus.BLOCK);
        client.setCompanyId(companyUtil.getCurrentUser().getId());
        clientRepository.save(client);
        messageService.sendVerifyCode(client.getId());
        return ResponceDTO.sendOkResponce(client.getPhone(),1,"Message sent a phone number ");


    }




    public String getPhoneNumberById(String id) {
        Optional<ClientEntity> clientEntityById = clientRepository.getClientEntityById(id);

        if (clientEntityById.isPresent()){
            return clientEntityById.get().getPhone();
        }
        return null;
    }

    public ResponseEntity activateClient(VerificationDTO verification) {
        if (messageService.getMessageCount(verification.getPhoneNumber())>=3){

            return ResponceDTO.sendBadRequestResponce(-1,"Limit out");
        }

        if (!messageService.checkCode(verification.getActivationCode(),verification.getPhoneNumber())){

            return ResponceDTO.sendBadRequestResponce(-1,"Activation code wrong");

        }
        return setActiveClientByPhone(verification.getPhoneNumber());
    }


    public ResponseEntity setActiveClientByPhone(String phone){
        clientRepository.setActiveClient(phone);
        return ResponceDTO.sendOkResponce(1,"ok");
    }

    public ResponseEntity update(String id, ClientUpdateDTO update) {

        if (!existsById(id)){
            return ResponceDTO.sendBadRequestResponce(-1,"Client not found");
        }

        if (clientRepository.existsByPhone(update.getPhone())){
            return ResponceDTO.sendBadRequestResponce(-1,"Phone number is exists");

        }
        Optional<ClientEntity> byId = clientRepository.findById(id);

        ClientEntity client = byId.get();

        client.setName(update.getName());
        client.setStatus(GeneralStatus.BLOCK);
        client.setMiddleName(update.getMiddleName());
        client.setSurname(update.getSurname());
        client.setPassword(update.getPassword());
        client.setCompanyId( companyUtil.getCurrentUser().getId());

        clientRepository.save(client);
        messageService.sendVerifyCode(id);
        return ResponceDTO.sendOkResponce(1,"Updated") ;

    }

    public ResponseEntity filter(ClientFilterDTO filter) {


        return ResponseEntity.ok(clientFilter.clientFiter(filter));
    }





}
