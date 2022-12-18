package uz.uzcard.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.dto.CompanyUpdateDTO;
import uz.uzcard.dto.ProfileRegistrationDTO;
import uz.uzcard.service.CompanyService;
import uz.uzcard.service.ProfileService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {




    @Autowired
    private ProfileService profileService;





    @PostMapping("/create")
    @ApiOperation(value = "Api for create profile " ,nickname = " API for create profile" ,notes = "create profile only admin ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    @PreAuthorize("ROLE_SUPER_ADMIN")
    public ResponseEntity create(@Valid @RequestBody ProfileRegistrationDTO create){

        return profileService.create(create);
    }













}
