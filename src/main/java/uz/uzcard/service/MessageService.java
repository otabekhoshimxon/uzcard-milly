package uz.uzcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.uzcard.dto.message.MessageRequestDTO;
import uz.uzcard.dto.message.MessageResponceDTO;
import uz.uzcard.entity.MessageEntity;
import uz.uzcard.repository.MessageRepository;
import uz.uzcard.util.RandomUtil;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ClientService clientService;




    private String messageUrl="https://api.smsfly.uz/";

    private String messageKey="4b77045c-3a12-11eb-9bb4-6799ca27647f";


    public void sendVerifyCode(String id) {

        String phoneNumberById = clientService.getPhoneNumberById(id);
        String code = RandomUtil.getRandomSmsCode();
        String message = "Hurmatli  mijoz sizga ro'yxatdan o'tish  uchun \n himoya kodi: [ " + code + " ]" +
                "" +
                "" +
                "" +
                "  ";

        MessageResponceDTO responseDTO = send(phoneNumberById, message);

        MessageEntity entity = new MessageEntity();
        entity.setPhone(phoneNumberById);
        entity.setCode(code);
        entity.setStatus(responseDTO.getStatus());

        messageRepository.save(entity);

    }


    private MessageResponceDTO send(String phone, String message) {
        MessageRequestDTO requestDTO = new MessageRequestDTO();
        requestDTO.setKey(messageKey);
        requestDTO.setPhone(phone);
        requestDTO.setMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageRequestDTO> entity = new HttpEntity<MessageRequestDTO>(requestDTO, headers);
        MessageResponceDTO response = restTemplate.postForObject(messageUrl, entity, MessageResponceDTO.class);
        response.setStatus(true);
        return response;
    }

    public int getMessageCount(String phoneNumber) {

        return messageRepository.getMessageCount(phoneNumber);

    }

    public boolean checkCode(String activationCode,String phone) {

        String lastMessageCodeByPhone = messageRepository.getLastMessageCodeByPhone(phone);
        return lastMessageCodeByPhone.equals(activationCode);

    }
}