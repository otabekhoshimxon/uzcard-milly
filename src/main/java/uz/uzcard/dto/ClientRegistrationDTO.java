package uz.uzcard.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationDTO {
    private String name;
    private String surname;
    private String middleName;
    private String username;
    private String phone;
    private String email;

}
