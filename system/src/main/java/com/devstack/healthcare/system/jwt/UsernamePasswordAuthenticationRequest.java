package com.devstack.healthcare.system.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}