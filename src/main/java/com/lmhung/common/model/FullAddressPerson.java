package com.lmhung.common.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FullAddressPerson {
    private Person person;
    private Address address;
}
