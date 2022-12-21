package uz.uzcard.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.dto.client.ClientCreateDTO;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.util.MD5PasswordGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

@Entity
@Table(name = "client",uniqueConstraints = @UniqueConstraint(columnNames = {"passportSeria", "passportNumber"}))

public class ClientEntity implements BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column
    private String name;
    @Column
    private String surname;

    @Column
    private String middleName;

    @Column
    private String phone;

    @Column
    private String passportSeria;
    @Column
    private String passportNumber;
    @JoinColumn(name = "companyId",insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    @Column
    private String companyId;

    @Column
    @Size(min = 5,max = 250)
    private String password;
    @Column
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status=GeneralStatus.ACTIVE;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralRole role=GeneralRole.CLIENT;

    @Column
    private Boolean visible=Boolean.TRUE;

    public ClientEntity() {
    }

    public ClientEntity(ClientCreateDTO dto) {
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.middleName = dto.getMiddleName();
        this.phone = dto.getPhone();
        this.passportSeria = dto.getPassportSeria();
        this.passportNumber = dto.getPassportNumber();
        this.role = GeneralRole.CLIENT;
        this.password= MD5PasswordGenerator.getMd5Password(dto.getPassword());

    }
}



