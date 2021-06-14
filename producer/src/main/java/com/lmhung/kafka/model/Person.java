package com.lmhung.kafka.model;

import com.lmhung.common.MyUtils;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.UUID;

@Data
@ToString
@Slf4j
public class Person {
    private String id;
    private String userName;
    private String fullName;
    private String addressId;
    private String hairColor;
    private String company;
    private int age;

    public static Person randomValue() {
        var person = new Person();
        person.setId(UUID.randomUUID().toString());
        var fakeName = MyUtils.fakeName();
        person.setFullName(fakeName.fullName());
        person.setUserName(fakeName.username());
        person.setAge(new Random().nextInt(100));
        person.setAddressId(MyUtils.random("[A-Za-z]{3,10}"));
        person.setHairColor(MyUtils.fakeColor());
        person.setCompany(MyUtils.fakeCompany().name());
        return person;
    }
}
