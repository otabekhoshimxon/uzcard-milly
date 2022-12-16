package uz.uzcard.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.dto.CompanyUpdateDTO;
import uz.uzcard.service.CompanyService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {




    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    @ApiOperation(value = "Api for create company " ,nickname = " API for create company" ,notes = "create company only admin ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")

    })
    @PreAuthorize("ROLE_ADMIN")
    public ResponseEntity create(@Valid @RequestBody CompanyRegistrationDTO create){

        return companyService.create(create);
    }


    @PutMapping("/update/{id}")
    @ApiOperation(value = "Api for update company " ,nickname = " API for update company" ,notes = "update company only admin ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")

    })
    @PreAuthorize("ROLE_ADMIN")
    public ResponseEntity update(@PathVariable("id") String id,@Valid @RequestBody CompanyUpdateDTO update){

        return companyService.update(id,update);
    }












}
