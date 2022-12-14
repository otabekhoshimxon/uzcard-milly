package uz.uzcard.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegistrationDTO {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Address is required")
    private String address;
    @NotNull(message = "Service Persentage is required")
    private Double servicePersentage;
    @Email(message = "Email is not valid")
    private String email;
    @NotNull(message = "Phone is required")
    private String phone;
    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;






}
