package uz.uzcard.dto.message;

import lombok.Data;

@Data
public class MessageRequestDTO {

    private String key;
    private String phone;
    private String message;
}
