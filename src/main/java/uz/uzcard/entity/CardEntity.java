package uz.uzcard.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.util.CardNumberGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String prefix;

    @Column(unique = true,nullable = false)
    private String number;

    @Column(nullable = false)
    private String phone;

    @Column
    private LocalDateTime expiredDate = LocalDateTime.now().plusYears(5);

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private GeneralStatus status=GeneralStatus.ACTIVE;

    @JoinColumn(name = "clientId",insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ClientEntity client;

    private String clientId;


}



