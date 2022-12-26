package uz.uzcard.dto.card;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class CardCreateDTO {

    @NotNull(message = "Balance is required")
    private Long balance;
    @NotNull(message = "Client id is required")
    private String clientId;
}
