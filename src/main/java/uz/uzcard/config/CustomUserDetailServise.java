package uz.uzcard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.uzcard.repository.ClientRepository;
import uz.uzcard.repository.CompanyRepository;
import uz.uzcard.repository.ProfileRepository;

@Component
public class CustomUserDetailServise implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ClientRepository clientRepository;




    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {



        if (profileRepository.existsByPhone(phone)){
            return new CustomUserDetails(profileRepository.getByPhone(phone).get());
        }

        if (companyRepository.existsByPhone(phone)){
            return new CustomUserDetails(companyRepository.getByPhone(phone).get());
        }
        if (clientRepository.existsByPhone(phone)){
            return new CustomUserDetails(clientRepository.getByPhone(phone).get());
        }

        return null;
    }
}
