package uz.uzcard.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.AssignPhoneDTO;
import uz.uzcard.dto.card.CardCreateDTO;
import uz.uzcard.dto.card.CardPhoneDTO;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity changeStatus(@PathVariable("id") String id ){



        return cardService.changeStatusCard(id);

    }
    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/assignPhone")
    @ApiOperation(value = "Api for change status card " ,nickname = " API for change status card" ,notes = "change status card only BANK  Payment only blocked card")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity assignPhone(@RequestBody AssignPhoneDTO assignPhone ){
        return cardService.assignPhone(assignPhone);

    }


    @PreAuthorize("hasRole('PAYMENT') or hasRole('BANK')")
    @GetMapping("/getCardByCardId/{id}")
    @ApiOperation(value = "Api for get card by id " ,nickname = " API for get card" ,notes = "get card by id only Payment ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCardByCardId(@PathVariable("id") String id){
        return cardService.getCardByCardId(id);

    }

    @PreAuthorize("hasRole('PAYMENT') or hasRole('BANK')")
    @GetMapping("/getCardListByPhone")
    @ApiOperation(value = "Api for get card by Phone " ,nickname = " API for get card" ,notes = "get card by Phone only Payment ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCardListByPhone(@RequestBody CardPhoneDTO phone){
        return cardService.getCardListByPhone(phone);

    }
  @PreAuthorize("hasRole('PAYMENT') or hasRole('BANK')")
    @GetMapping("/getCardListByClientId/{id}")
    @ApiOperation(value = "Api for get card by clientId " ,nickname = " API for get card" ,notes = "get card by id only Payment ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCardListByClientId(@PathVariable String id){
        return cardService.getCardListByClientId(id);

    }









}
