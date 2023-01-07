package uz.uzcard.dto;

import lombok.Data;

@Data
public class CardInfoDTO {

    private String id;
    private String number;
    private String phone;
    private ClientShortInfoDTO client;


}
