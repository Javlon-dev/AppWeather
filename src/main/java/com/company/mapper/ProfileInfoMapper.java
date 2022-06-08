package com.company.mapper;

import com.company.dto.BaseDTO;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInfoMapper extends BaseDTO {

    private String name;

    private String surname;

    private String email;

    private ProfileStatus status;

    private ProfileRole role;

    private LocalDateTime deletedDate;

}
