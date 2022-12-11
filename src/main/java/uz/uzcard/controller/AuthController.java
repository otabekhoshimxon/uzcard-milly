package uz.uzcard.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Authorization api")
@RequestMapping("/auth")
public class AuthController {


    @GetMapping("/login")
    @PreAuthorize("ROLE_ADMIN")
    @ApiOperation(value = "Api for login" ,nickname = "Login API" ,notes = "login")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Muvaffaqqiyatli"),
                           @ApiResponse(code = 403,message = "Ruxsat yo'q "),
                           @ApiResponse(code = 401,message = "Avtorizatsiyadan o'tilmagan "),
                           @ApiResponse(code = 404,message = "Mavjud bo'lmagan API ")

    })
    public String login(){
        return "login";
    }




}
