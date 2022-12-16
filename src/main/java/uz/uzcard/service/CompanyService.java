package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.dto.CompanyUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.CompanyRepository;
import uz.uzcard.util.JwtUtil;
import uz.uzcard.util.MD5PasswordGenerator;

import java.util.Objects;
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

    public ResponseEntity create(CompanyRegistrationDTO dto) {

        if (existsByUsername(dto.getUsername())){
            return ResponceDTO.sendBadRequestResponce(-1,"Already registered");
        }

        CompanyEntity company=new CompanyEntity(dto);
        companyRepository.save(company);
        return ResponceDTO.sendAuthorizationToken(company.getUsername(), JwtUtil.encodeId(company.getId()));

    }

    public ResponseEntity update(String id,CompanyUpdateDTO update) {


        if (!existsById(id)){
            return ResponceDTO.sendBadRequestResponce(-1,"Company not found");

        }

        if (existsByUsername(update.getUsername())){
            return ResponceDTO.sendBadRequestResponce(-1,"Username already taken");
        }

        companyRepository.changeUsernameAndPasswordById(id,update.getUsername(), MD5PasswordGenerator.getMd5Password(update.getPassword()));



        return ResponceDTO.sendOkResponce(1,"Company update success");

    }








    public CompanyEntity getById(String id){
        Optional<CompanyEntity> byId = companyRepository.findById(id);
        return byId.orElse(null);
    }
}
