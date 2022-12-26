package uz.uzcard.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.card.CardCreateDTO;
import uz.uzcard.service.CardService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/card")
@Api(tags = "Card controller")
public class CardController {



    @Autowired
    private CardService cardService;


    @PreAuthorize("ROLE_BANK")
    @PostMapping("/create")
    @ApiOperation(value = "Api for create card " ,nickname = " API for create card" ,notes = "create card only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity create(@Valid @RequestBody CardCreateDTO cardCreate){



        return cardService.createCard(cardCreate);

    }


    @PreAuthorize("hasRole('BANK') or hasRole('PAYMENT')")
    @PutMapping("/changeStatus/{id}")
    @ApiOperation(value = "Api for change status card " ,nickname = " API for change status card" ,notes = "change status card only BANK  Payment only blocked card")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity changeStatus(@PathVariable("id") String id ){



        return cardService.changeStatusCard(id);

    }









}
