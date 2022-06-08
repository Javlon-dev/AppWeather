package com.company.dto.request;

import com.company.enums.profile.ProfileRole;
import com.company.enums.profile.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileFilterDTO {

    private String name;

    private String surname;

    private String email;

    private ProfileStatus status;

    private ProfileRole role;

    private Boolean deletedDate = true;

    private Boolean createdDate = true;

}
