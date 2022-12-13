package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.entity.ProfileEntity;
import uz.uzcard.interfaces.BaseService;
import uz.uzcard.repository.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileService  extends BaseService {


    @Autowired
    private ProfileRepository profileRepository;

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

    public void save(ProfileEntity profile) {
        if (!existsByUsername(profile.getUsername())){
            profileRepository.save(profile);
        }


    }
}
