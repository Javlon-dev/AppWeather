package com.company.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {

    private String email;

    private String profileId;

    public JwtDTO(String email) {
        this.email = email;
    }

}
