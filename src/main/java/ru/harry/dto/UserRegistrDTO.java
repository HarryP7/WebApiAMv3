package ru.harry.dto;


import javax.validation.constraints.Size;

public class UserRegistrDTO {
    //@Size(min = 4, max = 255, message = "Minimum login length: 4 characters")
    private String login;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    //@Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
