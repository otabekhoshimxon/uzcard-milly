package uz.uzcard.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponceDTO {


    private String phone;
    private String message;
    private Integer status;

    public ResponceDTO(String phone, String message) {
        this.phone = phone;
        this.message = message;
        this.status=1;
    }

    public ResponceDTO(Integer code, String message) {
        this.status = code;
        this.message = message;
    }

    public ResponceDTO(String phone, Integer code, String message) {
        this.status = code;
        this.message = message;
        this.phone = phone;
    }


    public static ResponseEntity sendBadRequestResponce(Integer code, String message) {
        return ResponseEntity.badRequest().body(new ResponceDTO(code, message));
    }

    public static ResponseEntity sendAuthorizationToken(String phone, String message) {
        return ResponseEntity.ok().body(new ResponceDTO(phone, message));
    }

    public static ResponseEntity sendOkResponce(Integer code, String message) {
        return ResponseEntity.ok().body(new ResponceDTO(code, message));
    }

    public static ResponseEntity sendOkResponce(String phone, Integer code, String message) {
        return ResponseEntity.ok().body(new ResponceDTO(phone, code, message));
    }


    public ResponceDTO() {
    }
}
