package com.example.demo.Controllers;

import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegisterData;
import com.example.demo.DTO.UserRegisterDto;
import com.example.demo.Services.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleAuthController
{
    @Autowired
    private GoogleAuthService gAuth;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto)
    {
        UserRegisterData userRegisterData = new UserRegisterData();

        try {
            String secretKey = gAuth.generateKey();
            userRegisterData.setPassword(userRegisterDto.getPassword());
            userRegisterData.setUsername(userRegisterDto.getUsername());
            userRegisterData.setSecretKey(secretKey);

            gAuth.addUser(userRegisterData);

            return ResponseEntity.ok("User add");
        } catch (Exception ex)
        {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto)
    {
        String key = gAuth.getSecretKey(userLoginDto);

        if (key.isEmpty())
        {
            return ResponseEntity.ok("No user found");
        }

        boolean isValid = gAuth.isValid(key, userLoginDto.getCode());

        if (isValid)
        {
            return ResponseEntity.ok("Login Success");
        }
        else
        {
            return ResponseEntity.status(401).body("Invalid code");
        }
    }
}
