package uz.uzcard.dto.client;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientFilterDTO {
    private String name;
    private String surname;
    private String middleName;
    private String password;
    private String phone;
    private String passportSeria;
    private String passportNumber;


}
