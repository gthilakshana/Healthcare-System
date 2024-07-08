package com.devstack.healthcare.system.service.impl;

import com.devstack.healthcare.system.auth.ApplicationUser;
import com.devstack.healthcare.system.entity.User;
import com.devstack.healthcare.system.entity.UserRole;
import com.devstack.healthcare.system.entity.UserRoleHasUser;
import com.devstack.healthcare.system.repo.UserRepo;
import com.devstack.healthcare.system.repo.UserRoleHasUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.devstack.healthcare.system.security.ApplicationUserRole.ADMIN;
import static com.devstack.healthcare.system.security.ApplicationUserRole.DOCTOR;

@Service
public class ApplicationUserServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;

    public ApplicationUserServiceImpl(UserRepo userRepo, UserRoleHasUserRepo userRoleHasUserRepo) {
        this.userRepo = userRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User selectedUser = userRepo.findByUsername(username);
        if (selectedUser==null){
            throw new UsernameNotFoundException(String.format("username %s not found",username));
        }

        List<UserRoleHasUser> userRoles = userRoleHasUserRepo.findByUserId(selectedUser.getId());
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for (UserRoleHasUser userRole:userRoles){
            if (userRole.getUserRole().getRoleName().equals("ADMIN")){
                grantedAuthorities.addAll(ADMIN.getSimpleGrantedAuthorities());
            }
            if (userRole.getUserRole().getRoleName().equals("DOCTOR")){
                grantedAuthorities.addAll(DOCTOR.getSimpleGrantedAuthorities());
            }
        }

        return new ApplicationUser(
                grantedAuthorities,
                selectedUser.getPassword(),
                selectedUser.getUsername(),
                selectedUser.isAccountNonExpired(),
                selectedUser.isAccountNonLocked(),
                selectedUser.isCredentialsNonExpired(),
                selectedUser.isEnabled()
        );

    }
}
