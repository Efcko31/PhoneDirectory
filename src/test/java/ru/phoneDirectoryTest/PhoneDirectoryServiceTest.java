package ru.phoneDirectoryTest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.PhoneDirectory.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.phoneDirectoryTest.PersonsForPhoneDirectory.*;

public class PhoneDirectoryServiceTest {

    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    @Test
    void findEveryoneWhoLivesInTheCityTest() {
        assertEquals("+7-111-111-11-11",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityX("Москва").getFirst().getPhoneNumber());
        assertEquals("+7-777-777-77-77",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityX("Москва").getLast().getPhoneNumber());
        assertEquals("+7-222-222-22-22",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityX("Санкт-Петербург").getFirst().getPhoneNumber());
        assertEquals("+7-888-888-88-88",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityX("Санкт-Петербург").getLast().getPhoneNumber());
    }

    //2)найти людей без отчества, и вернуть место их проживания, фио, номер телефона
    @Test
    void findPeopleWithoutPatronymicTest() {
        var person = PHONE_DIRECTORY.findPeopleWithoutPatronymic();
        assertEquals(3, person.size());
        assertEquals("Москва", person.getFirst().getCityOfResidence());
    }

    //3)найти людей с профессией x, и вернуть информацию о них отсротирваную по городу
    @Test
    void findPeopleWithCertainProfessionTest() {
        assertEquals(List.of(
                        IVANOV_IVAN.getPerson(), PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson()),
                PHONE_DIRECTORY.findPeopleWithProfessionXAndSortByCity("Разработчик"));
        //assertEquals(List.of(
          //              denisDenisov.getPerson(), alekseyAlekseev.getPerson()),
            //    findPeopleWithProfessionXAndSortByCity("Таксист", phoneDirectory));
    }

    //4)найти n людей с определенной профессией
    @Test
    void findNPeopleWithTheSpecifiedProfessionTest() {
        Assertions.assertEquals(List.of(ALEKSEY_ALEKSEEV.getPerson()), PHONE_DIRECTORY.findNPeopleWithTheSpecifiedProfession(
                "Таксист", 1));
        Assertions.assertEquals(List.of(NIKOLAY_IVANOV.getPerson(), ALEKSANDR_ALEKSANDROV.getPerson()), PHONE_DIRECTORY.findNPeopleWithTheSpecifiedProfession(
                "Слесарь", 2));
        Assertions.assertEquals(List.of(), PHONE_DIRECTORY.findNPeopleWithTheSpecifiedProfession(
                "Слесарь", 0));
    }

    //5)осуществить прозвон всех людей с профессией x, с уточненим актуальности информации
    @Test
    void callAllPeopleWithProfessionXAndClarifyInformationTest() {
        Assertions.assertEquals(List.of(NIKOLAY_IVANOV.getPerson(), ALEKSANDR_ALEKSANDROV.getPerson(), ARTEM_ARTEMOV.getPerson(), OLEG_OLEGOV.getPerson()),
                PHONE_DIRECTORY.callAllPeopleWithProfessionX("Слесарь"));
        Assertions.assertEquals(List.of(ALEKSEY_ALEKSEEV.getPerson(), DENIS_DENISOV.getPerson()),
                PHONE_DIRECTORY.callAllPeopleWithProfessionX("Таксист"));
        Assertions.assertEquals(List.of(PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson(), IVANOV_IVAN.getPerson()),
                PHONE_DIRECTORY.callAllPeopleWithProfessionX("Разработчик"));
    }

    @Test
    void transferringDataToSQL() {
        PHONE_DIRECTORY.addNewPerson(new Person(
                "+7-888-858-88-00",
                "Алексей",
                "Алексеев",
                "",
                "Санкт-Петербург",
                "улица Гринькова, д.33, кв.76",
                "Таксист"));
    }

    @Test
    void deletePersonByPhoneNumber() {
        assertTrue(PHONE_DIRECTORY.deletePerson("+7-111-111-11-11"));
        assertFalse(PHONE_DIRECTORY.deletePerson("+7-123-333-42-11"));
        assertFalse(PHONE_DIRECTORY.deletePerson("+7-111-000-11-11"));
    }

    @Test
    void replaceUserDataTest() {
        assertTrue(PHONE_DIRECTORY.replaceUserData("+7-999-999-99-99", "lastName", "Грачевский"));
        assertTrue(PHONE_DIRECTORY.replaceUserData("+7-111-111-11-11", "phoneNUMBER", "8-911-321-43-21"));
        assertFalse(PHONE_DIRECTORY.replaceUserData("+7-111-000-11-11", "phoneNUMBER", "8-911-321-43-21"));

    }



}
