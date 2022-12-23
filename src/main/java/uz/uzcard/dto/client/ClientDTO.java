package uz.uzcard.dto.client;

import lombok.Data;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;

import java.time.LocalDateTime;

@Data
public class ClientDTO {

    private String id;
    private String name;
    private String surname;
    private String middleName;
    private String phone;
    private String passportSeria;
    private String passportNumber;
    private String companyId;
    private LocalDateTime createdDate;
    private GeneralStatus status;
    private GeneralRole role;
    private Boolean visible;


}
