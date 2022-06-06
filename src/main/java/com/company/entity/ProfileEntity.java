package com.company.entity;

import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = {"email","deleted_date"}))
@Getter
@Setter
public class ProfileEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}
