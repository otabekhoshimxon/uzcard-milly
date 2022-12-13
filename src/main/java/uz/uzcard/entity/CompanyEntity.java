package uz.uzcard.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.dto.CompanyRegistrationDTO;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;
import uz.uzcard.util.JwtUtil;
import uz.uzcard.util.MD5PasswordGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

@Entity
@Table(name = "company")
public class CompanyEntity extends BaseEntity {


    @Column
    private String address;
    @Column
    private String phone;



    public CompanyEntity() {
    }
    public CompanyEntity(CompanyRegistrationDTO dto){
        this.name=dto.getName();
        this.address=dto.getAddress();
        this.email=dto.getEmail();
        this.phone=dto.getPhone();
        this.username=dto.getUsername();
        this.password= MD5PasswordGenerator.getMd5Password(dto.getPassword());
    }
}
