package uz.uzcard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzcard.dto.transfer.TransferCreateDTO;
import uz.uzcard.dto.transfer.TransferStatusDTO;
import uz.uzcard.service.TransferService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transfer")
@Api(tags = "Transfer controller")
public class TransferController {



    @Autowired
    private TransferService transferService;





    @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @PostMapping("/create")
    @ApiOperation(value = "Api for create transaction " ,nickname = " API for create transaction" ,notes = "create transaction only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    public ResponseEntity create(@Valid @RequestBody TransferCreateDTO transferCreate) {

        return transferService.create(transferCreate);
    }


    @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @PutMapping("/changeStatus/{id}")
    @ApiOperation(value = "Api for change status transaction ", nickname = " API for change status transaction", notes = "change status transaction only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    public ResponseEntity changeStatus(@PathVariable("id")String id, @Valid @RequestBody TransferStatusDTO status) {

        return transferService.changeStatus(id,status);
    }

 @PreAuthorize("hasRole('PAYMENT' or hasRole('BANK'))")
    @PutMapping("/cancel/{id}")
    @ApiOperation(value = "Api for cancel transfer transaction ", nickname = " API for cancel transfer transaction", notes = "cancel transfer transaction only BANK ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Muvaffaqqiyatli"),
            @ApiResponse(code = 403, message = "Ruxsat yo'q "),
            @ApiResponse(code = 201, message = "Yaratildi "),
            @ApiResponse(code = 401, message = "Avtorizatsiyadan o'tilmagan "),
            @ApiResponse(code = 404, message = "Mavjud bo'lmagan API ")
    })
    public ResponseEntity cancel(@PathVariable("id")String id) {

        return transferService.cancel(id);
    }





}
