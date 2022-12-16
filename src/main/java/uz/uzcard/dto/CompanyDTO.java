package uz.uzcard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.uzcard.enums.GeneralStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {
    private String name;
    private String phone;
    private String address;
    private Double servicePersentage;
    private LocalDateTime createdDate;
    private GeneralStatus status;
}
