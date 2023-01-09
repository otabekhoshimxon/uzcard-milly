package uz.uzcard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.TransactionCreateDTO;
import uz.uzcard.dto.card.CardCreateDTO;
import uz.uzcard.dto.card.CardNumberDTO;
import uz.uzcard.dto.card.CardPhoneDTO;
import uz.uzcard.service.TransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transaction")
@Api(tags = "Transaction controller")
public class TransactionController {





    @Autowired
    private TransactionService transactionService;



    @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @GetMapping("/cardId/{id}")
    @ApiOperation(value = "Api for get transaction info by card ID  " ,nickname = " API for get transaction info by card ID " ,notes = "get transaction info by card ID  only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getByCardId(@PathVariable("id") String id ,
                                      @RequestParam(value = "page",defaultValue = "0") Integer page,
                                      @RequestParam(value = "size",defaultValue = "4") Integer size) {
        return transactionService.getByCardId(id,page,size);
    }
    @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @GetMapping("/cardNumber")
    @ApiOperation(value = "Api for get transaction info by card number  " ,nickname = " API for get transaction info by card number " ,notes = "get transaction info by card number  only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCardByNumber(@RequestBody CardNumberDTO cardNumber ,
                                      @RequestParam(value = "page",defaultValue = "0") Integer page,
                                      @RequestParam(value = "size",defaultValue = "4") Integer size) {
        return transactionService.getByCardNumber(cardNumber.getCardNumber(),page,size);
    }



    @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @GetMapping("/byProfileId/{id}")
    @ApiOperation(value = "Api for get transaction info by profile ID  " ,nickname = " API for get transaction info by profile ID " ,notes = "get transaction info by profile ID  only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCardByProfileId(@PathVariable("id") String id  ,
                                      @RequestParam(value = "page",defaultValue = "0") Integer page,
                                      @RequestParam(value = "size",defaultValue = "4") Integer size) {
        return transactionService.getByProfileId(id,page,size);
    }

  @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @GetMapping("/phone")
    @ApiOperation(value = "Api for get transaction info by phone  " ,nickname = " API for get transaction info by phone " ,notes = "get transaction info by phone  only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCardByProfileId(@RequestBody CardPhoneDTO phone,
                                      @RequestParam(value = "page",defaultValue = "0") Integer page,
                                      @RequestParam(value = "size",defaultValue = "4") Integer size) {
        return transactionService.getByPhone(phone,page,size);
    }



  @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @GetMapping("/getCreditByCardId/{cardId}")
    @ApiOperation(value = "Api for get transaction info by phone  " ,nickname = " API for get transaction info by phone " ,notes = "get transaction info by phone  only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity getCredit(@PathVariable("cardId")String  cardId,
                                      @RequestParam(value = "page",defaultValue = "0") Integer page,
                                      @RequestParam(value = "size",defaultValue = "5") Integer size) {
        return transactionService.getCreditByCardId(cardId,page,size);
    }







}
