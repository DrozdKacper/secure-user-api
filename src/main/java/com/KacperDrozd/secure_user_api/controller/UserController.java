package com.KacperDrozd.secure_user_api.controller;


import com.KacperDrozd.secure_user_api.entity.AuthRequest;
import com.KacperDrozd.secure_user_api.entity.UserInfo;
import com.KacperDrozd.secure_user_api.service.JwtService;
import com.KacperDrozd.secure_user_api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }



    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }



    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/users")
    public List<UserInfo> getAllUsers() {
        return service.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        service.deleteUserById(id);
    }


}