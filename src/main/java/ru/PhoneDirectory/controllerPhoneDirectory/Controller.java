package ru.PhoneDirectory.controllerPhoneDirectory;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.PhoneDirectory.DTO.FullNamePhoneNumb;
import ru.PhoneDirectory.DTO.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.Person;

import java.util.List;

import static ru.PhoneDirectory.Tests.Persons.PERSONS;
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
        return PERSONS.returnAllInformationAllPersons();
    }

    @GetMapping("/findEveryoneWhoLivesInTheCity")
    public List<FullNamePhoneNumb> returnPersonsWhoLivesInTheCity(@RequestParam("city") String city) {
        log.info("запрос на жителей города: {}", city);
        return PERSONS.findEveryoneWhoLivesInTheCityX(city);
    }

    @GetMapping(value = "/findPeopleWithoutPatronymic", produces = "application/xml")
    public List<FullNamePhoneNumbAddress> returnPeopleWithoutPatronymic() {
        log.info("запрос на людей без отчества");
        return PERSONS.findPeopleWithoutPatronymic();
    }

    @GetMapping("/findPeopleWithProfessionX")
    public List<Person> findPeopleWithProfessionXAndSortByCity(@RequestParam("profession") String profession) {
        log.info("запрос на людей с профессией: {}", profession);
        return PERSONS.findPeopleWithProfessionXAndSortByCity(profession);
    }

    //Запрос в формате JSON и ответ в формате JSON
    @PostMapping(value = "/addsNewPerson", produces = MediaType.APPLICATION_JSON_VALUE) //возвращает Json
    public String addsANewPerson(@RequestBody Person person) {
        return PERSONS.addNewPerson(person);

    }
    //  "phoneNumber": "+7-888-858-88-00",
    //    "firstName" : "Алексей",
    //    "LastName" : "Алексеев",
    //    "patronymic" : "",
    //    "cityOfResidence" :"Санкт-Петербург",
    //    "address" : "улица Гринькова, д.33, кв.76",
    //    "typeofActivity" : "Таксист"

    @PutMapping("/replaceUserData")
    public void replaceUserData() {

    }

    //ОСТАВИЛ дял примера
    @GetMapping(value = "/stringAsJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStringAsJson() {
        return "{\"message\":\"Привет, мир!\"}";
    }

    @GetMapping("/responseEntityAsString")
    public ResponseEntity<String> getResponseEntityAsString() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONObject.quote("Когда жизнь дарит тебе лимоны, преврати их в JSON!"));
    }

}
