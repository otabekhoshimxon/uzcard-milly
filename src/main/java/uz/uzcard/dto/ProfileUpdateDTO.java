package uz.uzcard.dto;

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
    private String username;
    private String password;


}
