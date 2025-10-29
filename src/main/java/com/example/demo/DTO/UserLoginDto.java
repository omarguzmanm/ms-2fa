package com.example.demo.DTO;

public class UserLoginDto {
    private String username;
    private String password;
    private int code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserLoginDto(String username, String password, int code) {
        this.username = username;
        this.password = password;
        this.code = code;
    }
}
