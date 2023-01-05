package uz.uzcard.dto;

import lombok.Data;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.enums.TransferStatus;

import java.time.LocalDateTime;
@Data
public class TransferResponceDTO {


    private String id;
    private String fromCard;
    private String toCard;
    private Double totalAmount;
    private Double serviceAmount;
    private Double servicePersentage;
    private TransferStatus status;
    private Double amount;
    private String companyId;
    private LocalDateTime createdDate;


}
