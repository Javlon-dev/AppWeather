package com.company.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AgentDTO {

    private String firstname;

    private String lastname;

    private String nickname;

    private String password;

    public AgentDTO(String firstname, String lastname, String nickname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
    }
}
