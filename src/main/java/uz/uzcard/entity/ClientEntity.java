package uz.uzcard.entity;

import lombok.*;

import javax.persistence.*;

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
    private String email;

    @Column
    private String passportSeria;
    @Column
    private String passportNumber;


}



