package uz.uzcard.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.dto.company.CompanyRegistrationDTO;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.util.MD5PasswordGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "company")
public class CompanyEntity implements BaseEntity, Serializable {

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
    private GeneralRole role;
    @Column
    private Boolean visible = Boolean.TRUE;
    @Column
    private String address;
    @Column
    private String phone;

    @Column
    private String cardPrefix;
    @Column
    private Double servicePersentage;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId",insertable = false,updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProfileEntity creator;

    @Column
    private String creatorId;

    public CompanyEntity() {
    }

    public CompanyEntity(String id) {
        this.id = id;
    }

    public CompanyEntity(CompanyRegistrationDTO dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.phone=dto.getPhone();
        this.role=GeneralRole.valueOf(dto.getRole());
        this.cardPrefix=dto.getCardPrefix();
        this.phone=dto.getPhone();
        this.password= MD5PasswordGenerator.getMd5Password(dto.getPassword());
        this.servicePersentage=dto.getServicePersentage();

    }
}
