package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.CompanyRepository;
import uz.uzcard.util.JwtUtil;

import java.util.Optional;

@Service
public class CompanyService  extends BaseService {

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public boolean existsById(String id) {

        return companyRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return companyRepository.existsByUsername(username);
    }

    public CompanyEntity getByUsername(String username){
        Optional<CompanyEntity> byUsername =
                companyRepository.getByUsername(username);

        return byUsername.orElse(null);
    }

    public ResponseEntity registration(CompanyRegistrationDTO dto) {

        if (existsByUsername(dto.getUsername())){
            return ResponceDTO.sendBadRequestResponce(-1,"Already registered");
        }

        CompanyEntity company=new CompanyEntity(dto);
        companyRepository.save(company);
        return ResponceDTO.sendAuthorizationToken(company.getUsername(), JwtUtil.encodeId(company.getId()));

    }
}
