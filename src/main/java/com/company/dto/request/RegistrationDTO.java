package com.company.dto.request;

import com.company.annotation.ValidEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class RegistrationDTO {

    @NotBlank(message = "Name required")
    @Length(min = 2, message = "Name length must be between 2 to more than")
    private String name;

    @NotBlank(message = "Surname required")
    @Length(min = 2, message = "Surname length must be between 2 to more than")
    private String surname;

    @ValidEmail(message = "Email required")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 8, message = "Password length must be between 8 to more than")
    private String password;

}
