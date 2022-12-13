package uz.uzcard.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "client",uniqueConstraints = @UniqueConstraint(columnNames = {"passportSeria", "passportNumber"}))

public class ClientEntity extends BaseEntity {


    @Column
    private String surname;

    @Column
    private String middleName;

    @Column
    private String phoneNumber;

    @Column
    private String passportSeria;
    @Column
    private String passportNumber;


}



