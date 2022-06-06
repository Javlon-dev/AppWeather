package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseDTO {

    protected String id;

    protected LocalDateTime createdDate;

    protected LocalDateTime updatedDate;

}
