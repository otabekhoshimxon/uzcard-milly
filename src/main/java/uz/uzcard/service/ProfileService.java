package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzcard.dto.profile.ProfileCreateDTO;
import uz.uzcard.dto.profile.ProfileFilterDTO;
import uz.uzcard.dto.profile.ProfileUpdateDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.entity.ProfileEntity;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.repository.ProfileFilterRepository;
import uz.uzcard.repository.ProfileRepository;
import uz.uzcard.util.MD5PasswordGenerator;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileFilterRepository filterRepository;

    public boolean existsById(String id) {
        return profileRepository.existsById(id);
    }

    public ProfileEntity getByPhone(String phone) {
        Optional<ProfileEntity> byUsername =
                profileRepository.getByPhone(phone);
        return byUsername.orElse(null);
    }
    public boolean existsByPhone(String username) {
        return profileRepository.existsByPhone(username);
    }




    public ResponseEntity create(ProfileCreateDTO create) {
        if (existsByPhone(create.getPhone())) {
            return ResponceDTO.sendBadRequestResponce(-1, "Username already taken");
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setPhone(create.getPhone());
        profile.setName(create.getName());
        profile.setSurname(create.getSurname());
        profile.setPassword(MD5PasswordGenerator.getMd5Password(create.getPassword()));
        profile.setStatus(GeneralStatus.BLOCK);
        profile.setRole(GeneralRole.ADMIN);
        profileRepository.save(profile);
        return ResponceDTO.sendOkResponce(1, "Success ");
    }



    public ResponseEntity update(String id, ProfileUpdateDTO update) {

        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponceDTO.sendBadRequestResponce(-1, "Profile not found");
        }
        if (existsByPhone(update.getPhone())) {
            return ResponceDTO.sendBadRequestResponce(-1, "Username already taken");
        }
        ProfileEntity profile = byId.get();
        profile.setName(update.getName());
        profile.setPassword(MD5PasswordGenerator.getMd5Password(update.getPassword()));
        profile.setSurname(update.getSurname());
        profile.setPhone(update.getPhone());
        profileRepository.save(profile);
        return ResponceDTO.sendOkResponce(1, "Success");


    }

    public ResponseEntity filter(ProfileFilterDTO filter) {

        return ResponseEntity.ok(filterRepository.profileFilter(filter));

    }
}
