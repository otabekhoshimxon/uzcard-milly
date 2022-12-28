package uz.uzcard.dto.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.uzcard.enums.GeneralStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {

    private String id;
    private String prefix;
    private Long balance;
    private String number;
    private String phone;
    private LocalDateTime expiredDate;
    private LocalDateTime createdDate;
    private GeneralStatus status;
    private String clientId;
    private Boolean visible;

}
