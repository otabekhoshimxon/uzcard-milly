package uz.uzcard.entity;

import lombok.Data;
import uz.uzcard.enums.MessageType;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    @Column
    private String code;
    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean status;
}
