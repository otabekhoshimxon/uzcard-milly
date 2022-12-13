package uz.uzcard.dto.responce;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ResponceDTO {


    private String username;
    private String token;


    private Integer status;

    public ResponceDTO(String username, String token) {
        this.username = username;
        this.token = token;
        this.status=1;
    }

    public ResponceDTO( Integer code ,String message) {
        this.status =code;
        this.token = message;
    }




    public static ResponseEntity sendBadRequestResponce( Integer code,String message){
        return ResponseEntity.badRequest().body(new ResponceDTO(code,message));
    }

    public static ResponseEntity sendAuthorizationToken( String username,String message){
        return ResponseEntity.ok().body(new ResponceDTO(username,message));
    }

    public static ResponseEntity sendOkResponce(Integer code, String message){
        return ResponseEntity.ok().body(new ResponceDTO(code,message));
    }


    public ResponceDTO() {
    }
}
