package com.company.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {

    private String profileId;

    private String email;

    public JwtDTO(String email) {
        this.email = email;
    }

}
