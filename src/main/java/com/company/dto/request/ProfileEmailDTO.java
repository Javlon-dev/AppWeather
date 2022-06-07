package com.company.dto.request;

import com.company.annotation.ValidEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileEmailDTO {

    @ValidEmail(message = "Email required")
    private String email;

}
