package com.company.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ProfileBioDTO {

    @NotBlank(message = "Name required")
    @Length(min = 2, message = "Name length must be between 2 to more than")
    private String name;

    @NotBlank(message = "Surname required")
    @Length(min = 2, message = "Surname length must be between 2 to more than")
    private String surname;

}
