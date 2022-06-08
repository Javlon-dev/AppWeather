package com.company.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class ProfilePasswordDTO {

    @NotBlank(message = "OldPassword required")
    private String oldPassword;

    @NotBlank(message = "Password required")
    @Size(min = 8, message = "Password length must be between 8 to more than")
    private String password;

}
