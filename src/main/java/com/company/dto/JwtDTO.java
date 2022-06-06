package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtDTO {

    private String email;

    public JwtDTO(String email) {
        this.email = email;
    }

}
