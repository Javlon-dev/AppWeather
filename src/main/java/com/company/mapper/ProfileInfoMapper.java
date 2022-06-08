package com.company.mapper;

import com.company.dto.BaseDTO;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProfileInfoMapper extends BaseDTO {

    private String id;

    private String name;

    private String surname;

    private String email;

    private ProfileStatus status;

    private ProfileRole role;

    private LocalDateTime deletedDate;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
