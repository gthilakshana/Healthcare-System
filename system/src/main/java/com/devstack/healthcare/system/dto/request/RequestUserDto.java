package com.devstack.healthcare.system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto {
    private long id;
    private String fullName;
    private String email;
    private String password;
}
