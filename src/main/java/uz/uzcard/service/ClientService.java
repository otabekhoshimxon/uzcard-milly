package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.dto.client.ClientCreateDTO;
import uz.uzcard.dto.client.ClientDTO;
import uz.uzcard.dto.client.ClientFilterDTO;
import uz.uzcard.dto.client.ClientUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.ClientEntity;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.repository.ClientFilterRepository;
import uz.uzcard.repository.ClientRepository;
import uz.uzcard.util.CompanyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {


        }

        Collection<? extends GrantedAuthority> authorities =
                companyUtil.getCurrentUser().getAuthorities();

        if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.equals(new SimpleGrantedAuthority("COMPANY")))){
            return ResponseEntity.ok(clientFilter.clientFilter(filter,companyUtil.getCurrentUser().getId()));
        }
        else {
            return ResponseEntity.ok(clientFilter.clientFilter(filter));

        }


    }


    public ResponseEntity getByProfileId(String profileId) {


        List<ClientEntity> byProfileId = clientRepository.getByProfileId(profileId);
        if (byProfileId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1,"Not found");
        }
        List<ClientDTO> list=new ArrayList<>();

        for (ClientEntity client : byProfileId) {
            ClientDTO dto=new ClientDTO();
            dto.setId(client.getId());
            dto.setName(client.getName());
            dto.setMiddleName(client.getMiddleName());
            dto.setSurname(client.getSurname());
            dto.setRole(client.getRole());
            dto.setCompanyId(client.getCompanyId());
            dto.setStatus(client.getStatus());
            dto.setPassportSeria(client.getPassportSeria());
            dto.setPassportNumber(client.getPassportNumber());
            dto.setPhone(client.getPhone());
            dto.setVisible(client.getVisible());
            list.add(dto);
        }

        return ResponseEntity.ok(list);

    }
}
