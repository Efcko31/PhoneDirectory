package ru.PhoneDirectory.DTO;

import lombok.Data;

@Data
public class FullNamePhoneNumb {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String patronymic;
}
