package com.lmhung.common.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Address {
    private String id;
    private String city;
    private String street;
    private String streetNumber;
    private String country;
    private String countryCode;
}
