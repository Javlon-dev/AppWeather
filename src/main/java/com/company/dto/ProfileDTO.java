package com.company.dto;

import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProfileDTO extends BaseDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private ProfileStatus status;

    private ProfileRole role;

    private LocalDateTime deletedDate;

}
