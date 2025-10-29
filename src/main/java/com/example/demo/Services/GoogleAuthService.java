package com.example.demo.Services;

import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegisterData;
import com.example.demo.Repo.GoogleAuthRepo;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleAuthService
{
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    @Autowired
    private GoogleAuthRepo googleAuthRepo;

    public String generateKey() {
        return gAuth.createCredentials().getKey();
    }

    public void addUser(UserRegisterData userRegisterData)
    {
        googleAuthRepo.save(userRegisterData);
    }

    public boolean isValid(String secret, int code)
    {
        return gAuth.authorize(secret, code);
    }

    public String getSecretKey(UserLoginDto userLoginDto)
    {
        UserRegisterData userRegisterData = googleAuthRepo.findByUsername(userLoginDto.getUsername()).orElse(null);

        assert userRegisterData != null;
        return userRegisterData.getSecretKey();
    }
}
