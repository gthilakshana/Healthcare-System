package com.devstack.healthcare.system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class RequestDoctorDto {
    private String name;
    private String address;
    private String contact;
    private double salary;
}
