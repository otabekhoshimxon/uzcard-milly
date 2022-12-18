package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.ProfileCreateDTO;
import uz.uzcard.dto.ProfileUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.ProfileEntity;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.ProfileRepository;
import uz.uzcard.util.MD5PasswordGenerator;

import java.util.Optional;

@Service
public class ProfileService  extends BaseService {


    @Autowired
    private ProfileRepository profileRepository;


    @Autowired
    private EMailServise mailServise;



    @Override
    public boolean existsById(String id) {
        return profileRepository.existsById(id);
    }



    public ProfileEntity getByUsername(String username){
        Optional<ProfileEntity> byUsername =
                profileRepository.getByUsername(username);
        return byUsername.orElse(null);
    }

    public boolean existsByUsername(String username) {
        return profileRepository.existsByUsername(username);
    }




    public ResponseEntity create(ProfileCreateDTO create) {


        if (existsByUsername(create.getUsername())){
            return ResponceDTO.sendBadRequestResponce(-1,"Username already taken");

        }
        ProfileEntity profile=new ProfileEntity();
        profile.setUsername(create.getUsername());
        profile.setName(create.getName());
        profile.setSurname(create.getSurname());
        profile.setPassword(MD5PasswordGenerator.getMd5Password(create.getPassword()));
        profile.setStatus(GeneralStatus.BLOCK);
        profile.setEmail(create.getEmail());
        profile.setRole(GeneralRole.ADMIN);
        ProfileEntity save = profileRepository.save(profile);
        mailServise.sendRegistrationEmail(create.getEmail(),save.getId());
        return ResponceDTO.sendOkResponce(1,"Message sent your email ");
    }

    public ResponseEntity verifyProfileById(String id) {

        if (!existsById(id)){
            return ResponceDTO.sendBadRequestResponce(-1,"Profile not found");

        }
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1,"Profile not found");

        }
        ProfileEntity profile = byId.get();
        profile.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(profile);
        return ResponceDTO.sendOkResponce(profile.getUsername(), 1,"Success");
    }

    public ResponseEntity update(String id, ProfileUpdateDTO update) {

        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()){
            return ResponceDTO.sendBadRequestResponce(-1,"Profile not found");
        }
        if (existsByUsername(update.getUsername())){
            return ResponceDTO.sendBadRequestResponce(-1,"Username already taken");

        }

        if (profileRepository.existsByEmail(update.getEmail())){
            return ResponceDTO.sendBadRequestResponce(-1,"Email already taken");

        }
        ProfileEntity profile = byId.get();
        profile.setName(update.getName());
        profile.setPassword(MD5PasswordGenerator.getMd5Password(update.getPassword()));
        profile.setSurname(update.getSurname());
        profile.setEmail(update.getEmail());
        profile.setUsername(update.getUsername());

        profileRepository.save(profile);
        return ResponceDTO.sendOkResponce(1,"Success");


    }
}
