package uz.uzcard.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.uzcard.dto.ClientRegistrationDTO;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.util.MD5PasswordGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString

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
    @JoinColumn(name = "companyId",insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    @Column
    private String companyId;



    public ClientEntity() {
    }

    public ClientEntity(ClientRegistrationDTO  dto) {
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.middleName = dto.getMiddleName();
        this.username = dto.getUsername();
        this.phoneNumber = dto.getPhone();
        this.passportSeria = dto.getPassportSeria();
        this.passportNumber = dto.getPassportNumber();
        this.email = dto.getEmail();
        this.role = GeneralRole.CUSTOMER;
        this.password= MD5PasswordGenerator.getMd5Password(dto.getPassword());

    }
}



