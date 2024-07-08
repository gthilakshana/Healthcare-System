package com.devstack.healthcare.system.api;

import com.devstack.healthcare.system.dto.request.RequestDoctorDto;
import com.devstack.healthcare.system.dto.request.RequestUserDto;
import com.devstack.healthcare.system.service.UserService;
import com.devstack.healthcare.system.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/visitor/signup")
    public ResponseEntity<StandardResponse> createDoctor(@RequestBody RequestUserDto userDto){
        userService.signup(userDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"user was saved!",
                        userDto.getFullName()),
                HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/verify", params = {"type"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DOCTOR')")
    public ResponseEntity<StandardResponse> verifyUser(
            @RequestParam String type,
            @RequestHeader("Authorization") String token
    ){

        return new ResponseEntity<>(
                new StandardResponse(200,"user state!",
                        userService.verifyUser(type,token)),
                HttpStatus.OK
        );
    }

}
