package uz.uzcard.dto;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreateDTO {
    private String name;
    private String surname;
    @Email
    private String email;
    private String username;
    private String password;


}
