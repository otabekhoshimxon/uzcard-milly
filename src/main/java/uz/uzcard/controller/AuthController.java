package uz.uzcard.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.AuthDTO;
import uz.uzcard.dto.VerificationDTO;
import uz.uzcard.service.AuthService;
import javax.validation.Valid;

@RestController
@Api(tags = "Authorization api")
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Api for login" ,nickname = "Login API" ,notes = "login")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")

    })
    public ResponseEntity login(@Valid @RequestBody AuthDTO dto){

        return authService.login(dto);
    }





    @PreAuthorize("ROLE_BANK")
    @PutMapping("/client/verification")
    public ResponseEntity verificationClient(@Valid @RequestBody VerificationDTO verification){

        return authService.verifyClient(verification);
    }


    @PreAuthorize("ROLE_BANK")
    @PutMapping("/card/verification")
    public ResponseEntity verificationCard(@Valid @RequestBody VerificationDTO verification){

        return authService.verifyCard(verification);
    }









}
