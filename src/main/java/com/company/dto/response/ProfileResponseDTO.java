package com.company.dto.response;


import com.company.dto.BaseDTO;
import com.company.enums.profile.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO extends BaseDTO {

    private String name;

    private String surname;

    private String email;

    private ProfileStatus status;

    private String token;
}
