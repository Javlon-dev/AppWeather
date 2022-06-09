package com.company.mapper;

import com.company.dto.BaseDTO;
import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileInfoMapper {

    private String id;

    private String name;

    private String surname;

    private String email;

    private ProfileStatus status;

    private ProfileRole role;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

}
