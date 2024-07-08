package com.devstack.healthcare.system.service.impl;

import com.devstack.healthcare.system.dto.request.RequestUserDto;
import com.devstack.healthcare.system.entity.User;
import com.devstack.healthcare.system.entity.UserRole;
import com.devstack.healthcare.system.entity.UserRoleHasUser;
import com.devstack.healthcare.system.exceptions.EntryNotFoundException;
import com.devstack.healthcare.system.jwt.JwtConfig;
import com.devstack.healthcare.system.jwt.JwtSecretKey;
import com.devstack.healthcare.system.repo.UserRepo;
import com.devstack.healthcare.system.repo.UserRoleHasUserRepo;
import com.devstack.healthcare.system.repo.UserRoleRepo;
import com.devstack.healthcare.system.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserRoleRepo userRoleRepo, UserRoleHasUserRepo userRoleHasUserRepo,
                           PasswordEncoder passwordEncoder, JwtConfig jwtConfig, SecretKey secretKey) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public void signup(RequestUserDto userDto) {
        UserRole userRole;
        if (userDto.getId()==1){
            userRole = userRoleRepo.findUserRoleByName("ADMIN");
        }else{
            userRole =  userRoleRepo.findUserRoleByName("DOCTOR");
        }

        if (userRole==null){
            throw new RuntimeException("User role not found");
        }

        User user = new User(
                userDto.getId(),
                userDto.getFullName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                true,
                true,
                true,
                true,
                null
        );

        UserRoleHasUser userData = new UserRoleHasUser(user,userRole);
        userRepo.save(user);
        userRoleHasUserRepo.save(userData);
    }

    @Override
    public boolean verifyUser(String type, String token) {

        String realToken =
                token.replace(jwtConfig.getTokenPrefix(),"");
        System.out.println(type);
        System.out.println(token);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(realToken);
        String username = claimsJws.getBody().getSubject();
        User selectedUser = userRepo.findByUsername(username);
        if(null==selectedUser){
            throw new EntryNotFoundException("Username not found!");
        }

        for(UserRoleHasUser roleUser:selectedUser.getUserRoleHasUsers()){
            if (roleUser.getUserRole().getRoleName().equals(type)){
                return true;
            }
        }
        return false;
    }
}