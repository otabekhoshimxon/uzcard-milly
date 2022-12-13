package uz.uzcard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.uzcard.repository.ClientRepository;
import uz.uzcard.repository.CompanyRepository;
import uz.uzcard.repository.ProfileRepository;
import uz.uzcard.service.ClientService;
import uz.uzcard.service.CompanyService;
import uz.uzcard.service.ProfileService;

@Component
public class CustomUserDetailServise implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ClientRepository clientRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        if (profileRepository.existsByUsername(username)){
            return new CustomUserDetails(profileRepository.getByUsername(username).get());
        }

        if (companyRepository.existsByUsername(username)){
            return new CustomUserDetails(companyRepository.getByUsername(username).get());
        }
        if (clientRepository.existsByUsername(username)){
            return new CustomUserDetails(clientRepository.getByUsername(username).get());
        }

        return null;
    }
}
