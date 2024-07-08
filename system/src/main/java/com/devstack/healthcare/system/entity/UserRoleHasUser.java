package com.devstack.healthcare.system.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRoleHasUser {

    @EmbeddedId
    private UserRoleHasUserKey id= new UserRoleHasUserKey();

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @MapsId("userRole")
    @JoinColumn(name = "role_id",nullable = false)
    private UserRole userRole;

    public UserRoleHasUser(User user, UserRole userRole) {
        this.user = user;
        this.userRole = userRole;
        this.id = new UserRoleHasUserKey(user.getId(), userRole.getId());
    }
}
