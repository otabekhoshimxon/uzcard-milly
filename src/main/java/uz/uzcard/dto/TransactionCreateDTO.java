package uz.uzcard.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TransactionCreateDTO {

    @NotNull
    private String cardId;
    @NotNull
    private String transferId;
    @Min(1000)
    private Double amount;
    @NotNull
    private String type;
}
