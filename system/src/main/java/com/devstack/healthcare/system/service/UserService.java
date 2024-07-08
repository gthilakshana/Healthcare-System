package com.devstack.healthcare.system.service;

import com.devstack.healthcare.system.dto.request.RequestUserDto;

public interface UserService {
    public void signup(RequestUserDto userDto);
    public boolean verifyUser(String type, String token);
}
