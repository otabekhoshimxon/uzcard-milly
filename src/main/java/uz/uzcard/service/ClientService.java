package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService  extends BaseService {


    @Autowired
    private ClientRepository clientRepository;





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
}
