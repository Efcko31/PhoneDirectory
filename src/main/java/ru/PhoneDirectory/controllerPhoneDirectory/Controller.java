package ru.PhoneDirectory.controllerPhoneDirectory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.PhoneDirectory.DTO.FullNamePhoneNumb;
import ru.PhoneDirectory.DTO.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectory;

import java.util.List;

import static ru.PhoneDirectory.PhoneDirectory.checksUsersByPhoneNumber;
import static ru.PhoneDirectory.phoneDirectoryRepository.PersonsForPhoneDirectory.phoneDirectory;

@RestController
@RequestMapping("/phoneDirectory")
@Slf4j
public class Controller {

    @GetMapping("/getAllPersons")
    public List<Person> returnAllPersons() {
        log.info("запрос на всех пользователей");
        return phoneDirectory;
    }

    @GetMapping("/getAllInformationAllPersons")
    public List<String> returnAllInformationAllPersons() {
        log.info("запрос текстовой информации всех пользователей");
        return PhoneDirectory.returnAllInformationAllPersons(phoneDirectory);
    }

    @GetMapping("/findEveryoneWhoLivesInTheCity")
    public List<FullNamePhoneNumb> returnPersonsWhoLivesInTheCity(@RequestParam("city") String city) {
        log.info("запрос на жителей города: {}", city);
        return PhoneDirectory.findEveryoneWhoLivesInTheCityX(city, phoneDirectory);
    }

    @GetMapping("/findPeopleWithoutPatronymic")
    public List<FullNamePhoneNumbAddress> returnPeopleWithoutPatronymic() {
        log.info("запрос на людей без отчества");
        return PhoneDirectory.findPeopleWithoutPatronymic(phoneDirectory);
    }

    @GetMapping("/findPeopleWithProfessionX")
    public List<Person> findPeopleWithProfessionXAndSortByCity(@RequestParam("profession") String profession) {
        log.info("запрос на людей с профессией: {}", profession);
        return PhoneDirectory.findPeopleWithProfessionXAndSortByCity(profession, phoneDirectory);
    }

    @PostMapping(value = "/addsNewPerson", produces = MediaType.APPLICATION_JSON_VALUE) //возвращает Json
    public String addsANewPerson(@RequestBody Person person) {
        return PhoneDirectory.addNewPerson(person, phoneDirectory);

    }
    //  "phoneNumber": "+7-888-858-88-00",
    //    "firstName" : "Алексей",
    //    "LastName" : "Алексеев",
    //    "patronymic" : "",
    //    "cityOfResidence" :"Санкт-Петербург",
    //    "address" : "улица Гринькова, д.33, кв.76",
    //    "typeofActivity" : "Таксист"

    @PutMapping("/replaceUserData/{phoneNumber}")
    public String replaceUserData(
            @PathVariable("phoneNumber") String phoneNumber,
            @RequestBody Person updateData) {
        log.info("Обновляем пользователя с номером телефона: {}", phoneNumber);

        for (Person person : phoneDirectory) {
            if (checksUsersByPhoneNumber(phoneNumber,person)) {
                person.setFirstName(updateData.getFirstName());
                person.setLastName(updateData.getLastName());
                person.setPatronymic(updateData.getPatronymic());
                person.setCityOfResidence(updateData.getCityOfResidence());
                person.setAddress(updateData.getAddress());
                person.setTypeofActivity(updateData.getTypeofActivity());

                System.out.println(person.toString());
                return "Пользователь обновлен!";
            }
        }
        return "Такого пользователя нет!";
    }

    @DeleteMapping("/deleteUser/{phoneNumber}")
    public String deleteUserOfPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        log.info("Запрос на удаление пользователя с номером телефона: {}", phoneNumber);
        if ( phoneDirectory.removeIf(p -> checksUsersByPhoneNumber(phoneNumber, p))) {
           return "Пользователь с номером телефона " + phoneNumber + " удален";
       } else {
           return "Пользователя с данным номером телефона нет";
       }


    }


}
