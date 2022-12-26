package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.company.CompanyDTO;
import uz.uzcard.dto.company.CompanyRegistrationDTO;
import uz.uzcard.dto.company.CompanyUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.repository.CompanyRepository;
import uz.uzcard.util.CurrentUserUtil;
import uz.uzcard.util.JwtUtil;
import uz.uzcard.util.MD5PasswordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CurrentUserUtil currentUserUtil;


    public boolean existsById(String id) {

        return companyRepository.existsById(id);
    }


    public ResponseEntity create(CompanyRegistrationDTO dto) {


        if (!(dto.getRole().equals(GeneralRole.BANK.name()) && dto.getRole().equals(GeneralRole.PAYMENT.name()))){
            return ResponceDTO.sendBadRequestResponce(-1,"Role is not valid");
        }
        if (companyRepository.existsByPhone(dto.getPhone())){
            return ResponceDTO.sendBadRequestResponce(-1,"Already registered");
        }



        CompanyEntity company=new CompanyEntity(dto);

        company.setCreatorId( currentUserUtil.getCurrentUser().getId());
        companyRepository.save(company);
        return ResponceDTO.sendAuthorizationToken(company.getPhone(), JwtUtil.encodeId(company.getId()));

    }


    public ResponseEntity update(String id,CompanyUpdateDTO update) {


        if (!existsById(id)){
            return ResponceDTO.sendBadRequestResponce(-1,"Company not found");

        }

        if (companyRepository.existsByPhone(update.getPhone())){
            return ResponceDTO.sendBadRequestResponce(-1,"Username already taken");
        }

        companyRepository.changePhoneAndPasswordById(id,update.getPhone(), MD5PasswordGenerator.getMd5Password(update.getPassword()));



        return ResponceDTO.sendOkResponce(1,"Company update success");

    }










    public CompanyEntity getById(String id){
        Optional<CompanyEntity> byId = companyRepository.findById(id);
        return byId.orElse(null);
    }


    public ResponseEntity<PageImpl> getAllCompany(Integer page, Integer size) {

        Sort sort=Sort.by("visible");
        Pageable of = PageRequest.of(page, size,sort);

        Page<CompanyEntity> all = companyRepository.findAll(of);
        List<CompanyEntity> content = all.getContent();
        List<CompanyDTO> getAll=new ArrayList<>();
        for (CompanyEntity company : content) {
            if (company.getVisible()){
                CompanyDTO companyDTO=new CompanyDTO();
                companyDTO.setPhone(company.getPhone());
                companyDTO.setName(company.getName());
                companyDTO.setStatus(company.getStatus());
                companyDTO.setAddress(company.getAddress());
                companyDTO.setPhone(company.getPhone());
                companyDTO.setServicePersentage(company.getServicePersentage());
                getAll.add(companyDTO);
            }

        }
        return ResponseEntity.ok(new PageImpl(getAll,of,all.getTotalElements()));
    }

    public ResponseEntity delete(String id) {

        if (!existsById(id)){
            return ResponceDTO.sendBadRequestResponce(-1,"Company not found");

        }

        companyRepository.setVisibleById(id);

        return ResponceDTO.sendOkResponce(1,"Company delete success");
    }
}
