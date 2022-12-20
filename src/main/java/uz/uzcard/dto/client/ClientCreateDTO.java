package uz.uzcard.dto.client;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateDTO {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Surname is required")
    private String surname;
    @NotNull(message = "Middle Name is required")
    private String middleName;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Phone is required")
    private String phone;
    @NotNull(message = "Passport Seria is required")
    private String passportSeria;
    @NotNull(message = "Passport Number is required")
    private String passportNumber;



}
