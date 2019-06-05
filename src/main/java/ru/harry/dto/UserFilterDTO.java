package ru.harry.dto;

import java.math.BigDecimal;

public class UserFilterDTO {
    private String fullName;
    private String city;
    /*private BigDecimal lat;
    private BigDecimal lng;*/

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
