package uz.uzcard.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.enums.TransactionStatus;
import uz.uzcard.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @JoinColumn(name = "cardId",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CardEntity card;
    private String cardId;

    @JoinColumn(name = "transferId",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TransferEntity transfer;
    private String transferId;


    private Double amount;

    private LocalDateTime createdDate=LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TransactionStatus status=TransactionStatus.CREATED;

    @Enumerated(EnumType.STRING)
    private TransactionType type;




}



