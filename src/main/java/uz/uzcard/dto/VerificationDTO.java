package uz.uzcard.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class VerificationDTO {

    @NotNull
    private String phoneNumber;
    @NotNull
    private String activationCode;
}
