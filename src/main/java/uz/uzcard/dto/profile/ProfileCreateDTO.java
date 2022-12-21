package uz.uzcard.dto.profile;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreateDTO {
    @NotNull
    private String name;
    @NotNull
    private String surname;

    @NotNull
    private String phone;
    @NotNull
    private String password;


}
