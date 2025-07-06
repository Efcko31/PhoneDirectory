package ru.PhoneDirectoryTest;


import org.junit.jupiter.api.Test;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectoryRepository.PhoneDirectoryRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.PhoneDirectory.PhoneDirectoryRepository.PersonsForPhoneDirectory.aleksandrAleksandrov;
import static ru.PhoneDirectory.PhoneDirectoryRepository.PersonsForPhoneDirectory.petrPetrov;
import static ru.PhoneDirectory.PhoneDirectoryRepository.PhoneDirectoryRepository.findPeopleWithoutPatronymic;

public class PhoneDirectorySQLTest {
    @Test
    void gettingDataDependingOnCityOfResidenceSQLTest() {
        assertEquals(new Person("Николай", "Иванов", "Васильевич",
                "+7-111-111-11-11"), getsUserDataAboutUserDependingOnCityResidence("Москва").getFirst());
        assertEquals(List.of(), getsUserDataAboutUserDependingOnCityResidence(null));
        assertEquals(List.of(), getsUserDataAboutUserDependingOnCityResidence("Севастополь"));
        assertEquals(new Person("Петр", "Петров", "Петрович",
                "+7-222-222-22-22"), getsUserDataAboutUserDependingOnCityResidence("Санкт-Петербург").getFirst());
    }

    public List<Person> getsUserDataAboutUserDependingOnCityResidence(String cityX) {

        List<Person> whoLiveInCityX;
        try {
            whoLiveInCityX = PhoneDirectoryRepository.findEveryoneWhoLivesInTheCityXUsingSQL(cityX);

            if (whoLiveInCityX.isEmpty()) {
                System.out.println("В этом городе никто не живёт ");
            } else {
                System.out.println("Найдены жители:");
                for (Person p : whoLiveInCityX) {
                    System.out.printf("%s %s: %s\n",
                            p.getFirstName(), p.getLastName(), p.getPhoneNumber());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return whoLiveInCityX;
    }

    @Test
    void searchForPersonPhoneNumberSQLTest() {
        assertEquals(aleksandrAleksandrov.getPerson(), lookingForPersonByPhoneNumber("+7-444-444-44-44"));
        assertEquals(null, lookingForPersonByPhoneNumber("+7-444-474-44-54"));
    }

    public Person lookingForPersonByPhoneNumber(String phoneNumber) {
        Person person = new Person();
        try {
            person = PhoneDirectoryRepository.findByPhoneNumber(phoneNumber);

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e);
        }
        return person;
    }

    @Test
    void findPeopleWithoutPatronymicSQLTest() throws SQLException {
        assertEquals(3, findingPeopleWithoutPatronymic().size());
        assertEquals("Москва", findPeopleWithoutPatronymic().getFirst().getCityOfResidence());
    }

    public List<Person> findingPeopleWithoutPatronymic() {
        List<Person> personsWithoutPatronymic = new ArrayList<>();
        try {
            personsWithoutPatronymic = findPeopleWithoutPatronymic();
        } catch (SQLException e) {
            System.out.println("Ошбика " + e);
        }
    return personsWithoutPatronymic;
    }
}
