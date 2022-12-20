package uz.uzcard.dto.company;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class CompanyUpdateDTO {

    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;


}
