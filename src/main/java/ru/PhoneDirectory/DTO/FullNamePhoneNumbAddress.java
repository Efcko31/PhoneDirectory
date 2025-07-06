package ru.PhoneDirectory.DTO;

import lombok.Data;

@Data
public class FullNamePhoneNumbAddress {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String cityOfResidence;
    private String address;
}
