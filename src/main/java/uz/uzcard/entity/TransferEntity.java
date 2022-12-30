package uz.uzcard.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.enums.TransferStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "transfer")
public class TransferEntity  {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    @JoinColumn(name = "fromCardId",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CardEntity fromCard;
    private String fromCardId;


    @JoinColumn(name = "toCardId",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CardEntity toCard;
    private String toCardId;
    private Long totalAmount;
    private Long amount;

    private Long service_amount;
    private Long service_percentage;

    private LocalDateTime createdDate=LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private TransferStatus status;


    @JoinColumn(name = "companyId",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CompanyEntity company;
    private String companyId;









}



