package uz.uzcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import uz.uzcard.entity.ProfileEntity;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.repository.ProfileRepository;
import uz.uzcard.service.ProfileService;
import uz.uzcard.util.MD5PasswordGenerator;

@SpringBootApplication
@EnableCaching
public class UzcardProjectApplication {


    @Autowired
    private ProfileRepository profileRepository;

    public static void main(String[] args) {


        SpringApplication.run(UzcardProjectApplication.class, args);


    }

    @Bean
    public CommandLineRunner runner() {

        return args -> {
            String username="Otabek13";
            if (!profileRepository.existsByUsername(username)) {
                ProfileEntity profile = new ProfileEntity();
                profile.setUsername(username);
                profile.setRole(GeneralRole.ADMIN);
                profile.setPassword(MD5PasswordGenerator.getMd5Password("1306"));
                profile.setSurname("Hoshimxon");
                profile.setName("Otabek");

                profileRepository.save(profile);
            }
        };

    }

}
