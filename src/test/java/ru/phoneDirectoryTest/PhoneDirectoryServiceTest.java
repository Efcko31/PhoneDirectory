package ru.phoneDirectoryTest;


import org.junit.jupiter.api.Test;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.phoneDirectoryTest.PersonsForPhoneDirectoryForTest.*;

public class PhoneDirectoryServiceTest {

    public static PhoneDirectoryService PHONE_DIRECTORY = new PhoneDirectoryService();

    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    @Test
    void findEveryoneWhoLivesInTheCityTest() {
        assertEquals("+7-111-111-11-11",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityN("Москва").getFirst().getPhoneNumber());
        assertEquals("+7-777-777-77-77",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityN("Москва").getLast().getPhoneNumber());
        assertEquals("+7-222-222-22-22",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityN("Санкт-Петербург").getFirst().getPhoneNumber());
        assertEquals("+7-888-888-88-88",
                PHONE_DIRECTORY.findEveryoneWhoLivesInTheCityN("Санкт-Петербург").getLast().getPhoneNumber());
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
        assertTrue(IVANOV_IVAN.getPerson().getPhoneNumber().equals(
                PHONE_DIRECTORY.findPeopleWithProfessionXAndSortByCity("Разработчик").getFirst().getPhoneNumber()));
        assertTrue(DENIS_DENISOV.getPerson().getPhoneNumber()
                .equals(PHONE_DIRECTORY.findPeopleWithProfessionXAndSortByCity("Таксист").getFirst().getPhoneNumber()));

        System.out.println(PHONE_DIRECTORY.findPeopleWithProfessionXAndSortByCity("Таксист"));
    }

    //4)найти n людей с определенной профессией
    @Test
    void findNPeopleWithTheSpecifiedProfessionTest() {
        assertEquals(ALEKSEY_ALEKSEEV.getPerson().getPhoneNumber(), PHONE_DIRECTORY.findNPeopleWithTheSpecifiedProfession(
                "Таксист", 1).getFirst().getPhoneNumber());
        assertEquals(NIKOLAY_IVANOV.getPerson().getPhoneNumber(), PHONE_DIRECTORY.findNPeopleWithTheSpecifiedProfession(
                "Слесарь", 2).getFirst().getPhoneNumber());
        assertEquals(List.of(), PHONE_DIRECTORY.findNPeopleWithTheSpecifiedProfession(
                "Слесарь", 0));
    }

    //5)осуществить прозвон всех людей с профессией x, с уточненим актуальности информации
    @Test
    void callAllPeopleWithProfessionXAndClarifyInformationTest() {
        assertEquals(NIKOLAY_IVANOV.getPerson().getPhoneNumber(),
                PHONE_DIRECTORY.callAllPeopleWithProfessionX("Слесарь").getFirst().getPhoneNumber());
        assertEquals(ALEKSEY_ALEKSEEV.getPerson().getPhoneNumber(),
                PHONE_DIRECTORY.callAllPeopleWithProfessionX("Таксист").getFirst().getPhoneNumber());
        assertEquals(PETR_PETROV.getPerson().getPhoneNumber(),
                PHONE_DIRECTORY.callAllPeopleWithProfessionX("Разработчик").getFirst().getPhoneNumber());
    }

//    @Test
//    void transferringDataToSQL() {
//        PHONE_DIRECTORY.addNewPerson(new Person(
//                "+7-888-858-88-00",
//                "Алексей",
//                "Алексеев",
//                "",
//                "Санкт-Петербург",
//                "улица Гринькова, д.33, кв.76",
//                "Таксист"));
//    }

    @Test
    void deletePersonByPhoneNumber() {
        assertTrue(PHONE_DIRECTORY.deletePerson("+7-111-111-11-11"));
        //assertFalse(PHONE_DIRECTORY.deletePerson("+7-123-333-42-11")); //.NoSuchElementException
        //assertFalse(PHONE_DIRECTORY.deletePerson("+7-111-000-11-11")); //.NoSuchElementException
    }

//    @Test
//    void fundPersonForPhoneNumber() {
//        assertEquals("Петров", PHONE_DIRECTORY.findPersonByPhoneNumber("82222222222").getLastName());
//    }

//    @Test
//    void checksUsersByPhoneNumberTest() {
//        assertTrue(PHONE_DIRECTORY.checksUsersByPhoneNumber("82222222222", PETR_PETROV.getPerson()));
//    }

    @Test
    void replaceUserDataTest() {
        assertEquals("Пушкин", PHONE_DIRECTORY.replaceUserData(
                new Person(
                        null,
                        null,
                        "Пушкин",
                        null,
                        null,
                        "Пушкенский переулок д.1",
                        null
                ), "89999999999"
        ).getLastName());

    }
}
