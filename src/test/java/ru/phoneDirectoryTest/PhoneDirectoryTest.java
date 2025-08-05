package ru.phoneDirectoryTest;


import org.junit.jupiter.api.Test;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.PhoneDirectory.PhoneDirectory.*;
import static ru.phoneDirectoryTest.PersonsForPhoneDirectory.*;

public class PhoneDirectoryTest {


    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    @Test
    void findEveryoneWhoLivesInTheCityTest() {
        assertEquals("+7-111-111-11-11",
                findEveryoneWhoLivesInTheCityX("Москва",phoneDirectory).getFirst().getPhoneNumber());
        assertEquals("+7-777-777-77-77",
                findEveryoneWhoLivesInTheCityX("Москва",phoneDirectory).getLast().getPhoneNumber());
        assertEquals("+7-222-222-22-22",
                findEveryoneWhoLivesInTheCityX("Санкт-Петербург",phoneDirectory).getFirst().getPhoneNumber());
        assertEquals("+7-888-888-88-88",
                findEveryoneWhoLivesInTheCityX("Санкт-Петербург",phoneDirectory).getLast().getPhoneNumber());
    }

    //2)найти людей без отчества, и вернуть место их проживания, фио, номер телефона
    @Test
    void findPeopleWithoutPatronymicTest() {
        var person = findPeopleWithoutPatronymic(phoneDirectory);
        assertEquals(3, person.size());
        assertEquals("Москва", person.getFirst().getCityOfResidence());
    }

    //3)найти людей с профессией x, и вернуть информацию о них отсротирваную по городу
    @Test
    void findPeopleWithCertainProfessionTest() {
        assertEquals(List.of(
                        ivanovIvan.getPerson(), petrPetrov.getPerson(), ilyaIlyiyov.getPerson()),
                findPeopleWithProfessionXAndSortByCity("Разработчик", phoneDirectory));
        //assertEquals(List.of(
          //              denisDenisov.getPerson(), alekseyAlekseev.getPerson()),
            //    findPeopleWithProfessionXAndSortByCity("Таксист", phoneDirectory));
    }

    //4)найти n людей с определенной профессией
    @Test
    void findNPeopleWithTheSpecifiedProfessionTest() {
        assertEquals(List.of(alekseyAlekseev.getPerson()), findNPeopleWithTheSpecifiedProfession(
                "Таксист", 1, phoneDirectory));
        assertEquals(List.of(nikolayIvanov.getPerson(), aleksandrAleksandrov.getPerson()), findNPeopleWithTheSpecifiedProfession(
                "Слесарь", 2, phoneDirectory));
        assertEquals(List.of(), findNPeopleWithTheSpecifiedProfession(
                "Слесарь", 0, phoneDirectory));
    }

    //5)осуществить прозвон всех людей с профессией x, с уточненим актуальности информации
    @Test
    void callAllPeopleWithProfessionXAndClarifyInformationTest() {
        assertEquals(List.of(nikolayIvanov.getPerson(), aleksandrAleksandrov.getPerson(), artemArtemov.getPerson(), olegOlegov.getPerson()),
                callAllPeopleWithProfessionX("Слесарь", phoneDirectory));
        assertEquals(List.of(alekseyAlekseev.getPerson(), denisDenisov.getPerson()),
                callAllPeopleWithProfessionX("Таксист", phoneDirectory));
        assertEquals(List.of(petrPetrov.getPerson(), ilyaIlyiyov.getPerson(), ivanovIvan.getPerson()),
                callAllPeopleWithProfessionX("Разработчик", phoneDirectory));
    }

    @Test
    void transferringDataToSQL() {
        PhoneDirectory.addNewPerson(new Person(
                "+7-888-858-88-00",
                "Алексей",
                "Алексеев",
                "",
                "Санкт-Петербург",
                "улица Гринькова, д.33, кв.76",
                "Таксист"), phoneDirectory);
    }



}
