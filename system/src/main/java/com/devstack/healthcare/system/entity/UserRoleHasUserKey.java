package com.devstack.healthcare.system.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoleHasUserKey implements Serializable {

    @Column(name = "user_id")
    private long user;
    @Column(name = "role_id")
    private long userRole;
}
