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
@Table(name = "profile")
public class ProfileEntity implements BaseEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column
    private String name;
    @Column
    private String username;

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
    private String surname;


}



