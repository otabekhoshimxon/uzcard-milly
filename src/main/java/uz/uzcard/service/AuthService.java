package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.uzcard.config.CustomUserDetails;
import uz.uzcard.dto.AuthDTO;
import uz.uzcard.dto.ClientRegistrationDTO;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.dto.responce.ResponceDTO;
import uz.uzcard.util.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ClientService clientService;


    @Autowired
    private AuthenticationManager authenticationManager;


    public ResponseEntity login(AuthDTO dto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();


        if (clientService.existsById(principal.getId())) {
            return ResponceDTO.sendAuthorizationToken(principal.getUsername(), JwtUtil.encodeId(principal.getId()));
        }
        if (profileService.existsById(principal.getId())) {
            return ResponceDTO.sendAuthorizationToken(principal.getUsername(), JwtUtil.encodeId(principal.getId()));
        }
        if (companyService.existsById(principal.getId())) {
            return ResponceDTO.sendAuthorizationToken(principal.getUsername(), JwtUtil.encodeId(principal.getId()));
        }

        return ResponceDTO.sendBadRequestResponce(-1,"Username or Password error");

    }





    public ResponseEntity clientRegistration(ClientRegistrationDTO dto) {

        return clientService.registration(dto);

    }

    public ResponseEntity verifyClient(VerificationDTO verification) {




        return  clientService.activateClient(verification);
    }

    public ResponseEntity verifyProfileById(String id) {

       return profileService.verifyProfileById(id);
    }
}
