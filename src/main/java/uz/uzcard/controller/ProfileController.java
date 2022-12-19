package uz.uzcard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.ProfileCreateDTO;
import uz.uzcard.dto.ProfileFilterDTO;
import uz.uzcard.dto.ProfileUpdateDTO;
import uz.uzcard.service.ProfileService;

import javax.validation.Valid;

@RestController
@Api(tags = "Profile controller")
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
    public ResponseEntity create(@Valid @RequestBody ProfileCreateDTO create){

        return profileService.create(create);
    }



  @PutMapping("/update/{id}")
    @ApiOperation(value = "Api for update profile " ,nickname = " API for update profile" ,notes = "update profile only admin ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    @PreAuthorize("ROLE_SUPER_ADMIN")
    public ResponseEntity update(@PathVariable("id") String id,@Valid @RequestBody ProfileUpdateDTO update){

        return profileService.update(id,update);
    }


  @PostMapping("/filter")
    @ApiOperation(value = "Api for filter profile " ,nickname = " API for filter profile" ,notes = "filter profile only admin ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    @PreAuthorize("ROLE_SUPER_ADMIN")
    public ResponseEntity filter(@Valid @RequestBody ProfileFilterDTO filter){

        return profileService.filter(filter);
    }













}
