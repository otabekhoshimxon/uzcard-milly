package uz.uzcard.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity{


    @Column
    private String surname;



}



