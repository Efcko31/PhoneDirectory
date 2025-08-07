package ru.PhoneDirectory.controllerPhoneDirectory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.PhoneDirectory.DTO.FullNamePhoneNumb;
import ru.PhoneDirectory.DTO.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectory;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/phoneDirectory")
@Slf4j
public class Controller {

    private PhoneDirectory phoneDirectory;

    @GetMapping("/getAllPersons")
    public List<Person> returnAllPersons() {
        log.info("запрос на всех пользователей");
        return phoneDirectory.getPersonList();
    }

    @GetMapping("/getAllInformationAllPersons")
    public List<String> returnAllInformationAllPersons() {
        log.info("запрос текстовой информации всех пользователей");
        return phoneDirectory.returnAllInformationAllPersons();
    }

    @GetMapping("/findEveryoneWhoLivesInTheCity")
    public List<FullNamePhoneNumb> returnPersonsWhoLivesInTheCity(@RequestParam("city") String city) {
        log.info("запрос на жителей города: {}", city);
        return phoneDirectory.findEveryoneWhoLivesInTheCityX(city);
    }

    @GetMapping(value = "/findPeopleWithoutPatronymic", produces = "application/xml")
    public List<FullNamePhoneNumbAddress> returnPeopleWithoutPatronymic() {
        log.info("запрос на людей без отчества");
        return phoneDirectory.findPeopleWithoutPatronymic();
    }

    @GetMapping("/findPeopleWithProfessionX")
    public List<Person> findPeopleWithProfessionXAndSortByCity(@RequestParam("profession") String profession) {
        log.info("запрос на людей с профессией: {}", profession);
        return phoneDirectory.findPeopleWithProfessionXAndSortByCity(profession);
    }

    //Запрос в формате JSON и ответ в формате JSON
    @PostMapping(value = "/addNewPerson", produces = MediaType.APPLICATION_JSON_VALUE) //возвращает Json
    public String addNewPerson(@RequestBody Person person) {
        log.info("запрос на добавление нового товарища!");
        return phoneDirectory.addNewPerson(person);

    }
    //  "phoneNumber": "+7-888-858-88-00",
    //    "firstName" : "Алексей",
    //    "LastName" : "Алексеев",
    //    "patronymic" : "",
    //    "cityOfResidence" :"Санкт-Петербург",
    //    "address" : "улица Гринькова, д.33, кв.76",
    //    "typeofActivity" : "Таксист"

    @PostMapping(value = "/addNewPersonFormatXML", consumes = "application/xml", produces = "application/xml")
    public String addANewPersonFormatXML(@RequestBody Person person) {
        return phoneDirectory.addNewPerson(person);
    }
    //<person>
    //    <phoneNumber>+7-888-858-88-00"</phoneNumber>
    //    <firstName>Алексей</firstName>
    //    <LastName>Алексеев</LastName>
    //    <patronymic></patronymic>
    //    <cityOfResidence>Санкт-Петербург</cityOfResidence>
    //    <address>улица Гринькова, д.33, кв.76</address>
    //    <typeofActivity>Таксист</typeofActivity>
    //</person>

    @PutMapping("/replaceUserData")
    public void replaceUserData() {//todo

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
