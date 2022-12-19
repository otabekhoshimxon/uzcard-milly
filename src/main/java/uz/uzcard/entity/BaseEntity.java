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
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class BaseEntity  implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String id;
    @Column
    protected String name;
    @Column
    protected String username;

    @Column
    @Size(min = 5,max = 250)
    protected String password;
    @Column
    protected LocalDateTime createdDate=LocalDateTime.now();
    @Column
    @Enumerated(EnumType.STRING)
    protected GeneralStatus status=GeneralStatus.ACTIVE;
    @Column
    @Enumerated(EnumType.STRING)
    protected GeneralRole role=GeneralRole.CUSTOMER;

    @Column
    protected Boolean visible=Boolean.TRUE;

}
