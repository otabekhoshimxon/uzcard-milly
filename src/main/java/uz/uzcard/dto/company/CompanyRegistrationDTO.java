package uz.uzcard.dto.company;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegistrationDTO {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Role is required")
    private String role;
    @NotNull(message = "Address is required")
    private String address;
    @NotNull(message = "Service Persentage is required")
    private Double servicePersentage;
    @NotNull(message = "Phone is not valid")
    private String phone;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Password is required")
    private String cardPrefix;


}
