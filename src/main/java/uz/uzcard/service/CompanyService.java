package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.CompanyDTO;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.dto.CompanyUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.CompanyRepository;
import uz.uzcard.util.JwtUtil;
import uz.uzcard.util.MD5PasswordGenerator;

import java.util.ArrayList;
import java.util.List;
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


    public ResponseEntity<PageImpl> getAllCompany(Integer page, Integer size) {

        Sort sort=Sort.by("visible");
        Pageable of = PageRequest.of(page, size,sort);

        Page<CompanyEntity> all = companyRepository.findAll(of);
        List<CompanyEntity> content = all.getContent();
        List<CompanyDTO> getAll=new ArrayList<>();
        for (CompanyEntity company : content) {
            if (company.getVisible()){
                CompanyDTO companyDTO=new CompanyDTO();
                companyDTO.setUsername(company.getUsername());
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
