package uz.uzcard.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.uzcard.config.CustomUserDetails;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.service.CompanyService;

@Component
public class CompanyUtil {

    public CustomUserDetails getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();

    }
}
