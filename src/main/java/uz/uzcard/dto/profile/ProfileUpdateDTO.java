package uz.uzcard.dto.profile;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {
    private String name;
    private String surname;
    private String phone;
    private String password;


}
