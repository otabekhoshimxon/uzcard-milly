package uz.uzcard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzcard.dto.TransactionCreateDTO;
import uz.uzcard.dto.card.CardCreateDTO;
import uz.uzcard.service.TransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transaction")
@Api(tags = "Transaction controller")
public class TransactionController {





    @Autowired
    private TransactionService transactionService;



    @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @PostMapping("/create")
    @ApiOperation(value = "Api for create transaction " ,nickname = " API for create transaction" ,notes = "create transaction only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })

    public ResponseEntity create(@Valid @RequestBody TransactionCreateDTO transactionCreate){
        return transactionService.create(transactionCreate);
    }







}
