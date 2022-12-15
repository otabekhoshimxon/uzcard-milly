package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.ClientRegistrationDTO;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.ClientRepository;
import uz.uzcard.util.CompanyUtil;

import java.util.Optional;

@Service
public class ClientService extends BaseService {


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyUtil companyUtil;

    @Autowired
    @Lazy
    private MessageService messageService;


    @Override
    public boolean existsById(String id) {
        return clientRepository.existsById(id);
    }



    public ClientEntity getByUsername(String username){
        Optional<ClientEntity> byUsername =
                clientRepository.getByUsername(username);

        return byUsername.orElse(null);
    }

    public boolean existsByUsername(String username){

             return    clientRepository.existsByUsername(username);


    }

    public ResponseEntity registration(ClientRegistrationDTO dto) {

        if (existsByUsername(dto.getUsername())){
            return ResponceDTO.sendBadRequestResponce(-1,"Username already registered");
        }
        if (clientRepository.existsByPhoneNumber(dto.getPhone())){
            return ResponceDTO.sendBadRequestResponce(-1,"Phone number already registered");
        }
        if (clientRepository.existsByEmail(dto.getEmail()))
            return ResponceDTO.sendBadRequestResponce(-1,"Email already registered");

        ClientEntity client=new ClientEntity(dto);
        client.setStatus(GeneralStatus.BLOCK);
        client.setCompanyId(companyUtil.getCurrentUser().getId());
        clientRepository.save(client);
        messageService.sendVerifyCode(client.getId());
        return ResponceDTO.sendOkResponce(client.getUsername(),1,"Message sent a phone number ");

    }


    public String getPhoneNumberById(String id) {
        Optional<ClientEntity> clientEntityById = clientRepository.getClientEntityById(id);

        if (clientEntityById.isPresent()){
            return clientEntityById.get().getPhoneNumber();
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
}
