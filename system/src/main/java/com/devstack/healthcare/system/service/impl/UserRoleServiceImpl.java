package com.devstack.healthcare.system.service.impl;

import com.devstack.healthcare.system.entity.UserRole;
import com.devstack.healthcare.system.repo.UserRepo;
import com.devstack.healthcare.system.repo.UserRoleRepo;
import com.devstack.healthcare.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public void initializeRoles() {
        if (userRoleRepo.count()==0){
            UserRole admin = new UserRole(1,"ADMIN","admin",null);
            UserRole doc = new UserRole(2,"DOCTOR","doctor",null);
            userRoleRepo.saveAll(List.of(admin,doc));
        }

    }
}