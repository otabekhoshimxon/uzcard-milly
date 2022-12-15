package uz.uzcard.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponceDTO {


    private String username;
    private String message;
    private Integer status;

    public ResponceDTO(String username, String message) {
        this.username = username;
        this.message = message;
        this.status=1;
    }

    public ResponceDTO(Integer code, String message) {
        this.status = code;
        this.message = message;
    }

    public ResponceDTO(String username, Integer code, String message) {
        this.status = code;
        this.message = message;
        this.username = username;
    }


    public static ResponseEntity sendBadRequestResponce(Integer code, String message) {
        return ResponseEntity.badRequest().body(new ResponceDTO(code, message));
    }

    public static ResponseEntity sendAuthorizationToken(String username, String message) {
        return ResponseEntity.ok().body(new ResponceDTO(username, message));
    }

    public static ResponseEntity sendOkResponce(Integer code, String message) {
        return ResponseEntity.ok().body(new ResponceDTO(code, message));
    }

    public static ResponseEntity sendOkResponce(String username, Integer code, String message) {
        return ResponseEntity.ok().body(new ResponceDTO(username, code, message));
    }


    public ResponceDTO() {
    }
}
