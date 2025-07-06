package ru.PhoneDirectory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person extends Object {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String cityOfResidence;
    private String address;
    private String typeofActivity;

    public Person(String firstName, String lastName, String patronymic, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    public Person(String cityOfResidence, String address, String firstName, String lastName, String phoneNumber) {
        this.cityOfResidence = cityOfResidence;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ФИО: " + lastName + " " + firstName + " " + patronymic
                + "; Гроод: " + cityOfResidence
                + "; Адрес: " + address
                + "; Профессия: " + typeofActivity
                + "; Телефон: " + phoneNumber + ".";
    }
}
