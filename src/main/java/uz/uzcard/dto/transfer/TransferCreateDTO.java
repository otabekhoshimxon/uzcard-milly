package uz.uzcard.dto.transfer;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TransferCreateDTO {

    @NotNull
    private String fromCardId;
    @NotNull
    private String toCardId;
    @NotNull
    @Min(1000)
    private Double amount;
}
