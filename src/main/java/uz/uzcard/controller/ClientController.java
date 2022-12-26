package uz.uzcard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.client.ClientCreateDTO;
import uz.uzcard.dto.client.ClientFilterDTO;
import uz.uzcard.dto.client.ClientUpdateDTO;
import uz.uzcard.dto.profile.ProfileCreateDTO;
import uz.uzcard.dto.profile.ProfileFilterDTO;
import uz.uzcard.dto.profile.ProfileUpdateDTO;
import uz.uzcard.service.ClientService;

import javax.validation.Valid;

@RestController
@Api(tags = "Client controller")
@RequestMapping("/api/v1/client")
public class ClientController {




    @Autowired
    private ClientService clientService;





    @PostMapping("/create")
    @ApiOperation(value = "Api for create client " ,nickname = " API for create client" ,notes = "create client only bank ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity create(@Valid @RequestBody ClientCreateDTO create){

        return clientService.create(create);
    }



  @PutMapping("/update/{id}")
    @ApiOperation(value = "Api for update client " ,nickname = " API for update client" ,notes = "update client only bank ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    @PreAuthorize("ROLE_COMPANY")
    public ResponseEntity update(@PathVariable("id") String id,@Valid @RequestBody ClientUpdateDTO update){

        return clientService.update(id,update);
    }


  @PostMapping("/filter")
    @ApiOperation(value = "Api for filter client " ,nickname = " API for filter client" ,notes = "filter client only bank ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    public ResponseEntity filter(@Valid @RequestBody ClientFilterDTO filter){

        return clientService.filter(filter);
    }

    @PreAuthorize("ROLE_ADMIN")
  @GetMapping("/getByProfileId/{id}")
    @ApiOperation(value = "Api for filter client " ,nickname = " API for get client" ,notes = "get clients only ADMIN ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getByProfileId(@PathVariable("id") String profileId){

        return clientService.getByProfileId(profileId);
    }













}
