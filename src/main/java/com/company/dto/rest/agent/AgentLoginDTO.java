package com.company.dto.rest.agent;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AgentLoginDTO {

//    @NotBlank(message = "Nickname required")
//    @Length(min = 5, message = "Nickname length must be between 5 to more than")
    private String nickname;

//    @NotBlank(message = "Password required")
    private String password;

}
