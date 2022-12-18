package uz.uzcard.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "email")
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column
    private String email;
    @Column
    private String message;
    @Column
    private LocalDateTime createdDate=LocalDateTime.now();

}
