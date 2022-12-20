package uz.uzcard.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.dto.company.CompanyRegistrationDTO;
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
@Table(name = "company")
public class CompanyEntity implements BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column
    private String name;
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
    @Column
    private String address;
    @Column
    private String phone;

    @Column
    private String cardPrefix;
    @Column
    private Double servicePersentage;
    @Column
    private String username;
    public CompanyEntity() {
    }

    public CompanyEntity(String id) {
        this.id=id;
    }
    public CompanyEntity(CompanyRegistrationDTO dto){
        this.name=dto.getName();
        this.address=dto.getAddress();
        this.phone=dto.getPhone();
        this.cardPrefix=dto.getCardPrefix();
        this.username=dto.getUsername();
        this.password= MD5PasswordGenerator.getMd5Password(dto.getPassword());
        this.servicePersentage=dto.getServicePersentage();
        this.role=GeneralRole.COMPANY;
    }
}
