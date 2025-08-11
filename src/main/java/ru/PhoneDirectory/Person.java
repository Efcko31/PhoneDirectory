package ru.PhoneDirectory;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "person")
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
    @XmlElement
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @XmlElement
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    @XmlElement
    public String getCityOfResidence() {
        return cityOfResidence;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }
    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @XmlElement
    public String getTypeofActivity() {
        return typeofActivity;
    }

    public void setTypeofActivity(String typeofActivity) {
        this.typeofActivity = typeofActivity;
    }

    @Override
    public String toString() {
        return "ФИО: " + lastName + " " + firstName + " " + patronymic
                + ";\n Город: " + cityOfResidence
                + ";\n Адрес: " + address
                + ";\n Профессия: " + typeofActivity
                + ";\n Телефон: " + phoneNumber + ".";
    }
}
