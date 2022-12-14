package uz.uzcard.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationDTO {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Surname is required")
    private String surname;
    @NotNull(message = "Middle Name is required")
    private String middleName;
    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Phone is required")
    private String phone;
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Passport Seria is required")
    private String passportSeria;
    @NotNull(message = "Passport Number is required")
    private String passportNumber;



}
