package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.ClientRegistrationDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.ClientRepository;
import uz.uzcard.util.CompanyUtil;

import java.util.Optional;

@Service
public class ClientService  extends BaseService {


    @Autowired
    private ClientRepository clientRepository;
  @Autowired
    private CompanyUtil companyUtil;





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
        client.setCompanyId(companyUtil.getCurrentUser().getId());
        clientRepository.save(client);
        return ResponceDTO.sendOkResponce(1,"Client successfully registered");

    }



}
