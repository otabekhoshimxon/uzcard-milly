package uz.uzcard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.entity.CompanyEntity;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientFilterResponseDTO {

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
