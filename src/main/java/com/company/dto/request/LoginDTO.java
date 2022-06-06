package com.company.dto.request;


import com.company.annotation.ValidEmail;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class LoginDTO {

    @ValidEmail(message = "Email required")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 8, message = "Password length must be between 8 to more than")
    private String password;

}
