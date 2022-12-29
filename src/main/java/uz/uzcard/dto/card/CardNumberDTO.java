package uz.uzcard.dto.card;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CardNumberDTO {
    @NotNull(message = "Card number is required ")
    private String cardNumber;
}
