package uz.uzcard.dto;

import lombok.Data;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.enums.TransactionStatus;
import uz.uzcard.enums.TransactionType;

import java.time.LocalDateTime;

@Data
public class TransactionInfoDTO {

    private String id;
    private CardInfoDTO fromCard;
    private CardInfoDTO toCard;
    private Double amount;
    private LocalDateTime createdDate;
    private TransactionStatus status;
    private TransactionType type;
}
