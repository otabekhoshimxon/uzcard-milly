package uz.uzcard.entity;


import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "company")
public class CompanyEntity extends BaseEntity {

    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String email;






}
