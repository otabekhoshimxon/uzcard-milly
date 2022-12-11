package uz.uzcard.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import uz.uzcard.enums.GeneralRole;
import uz.uzcard.enums.GeneralStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "strategy-uuid", strategy = "uuid")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
