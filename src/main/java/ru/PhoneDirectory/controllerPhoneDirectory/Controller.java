package ru.PhoneDirectory.controllerPhoneDirectory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectoryService;
import ru.PhoneDirectory.dto.FullNamePhoneNumb;
import ru.PhoneDirectory.dto.FullNamePhoneNumbAddress;

import java.util.List;

@RestController
@RequestMapping("/phoneDirectory")
@Slf4j //логирование
public class Controller {

    private final PhoneDirectoryService phoneDirectoryService;

    @Autowired
    public Controller(PhoneDirectoryService phoneDirectoryService) {
        this.phoneDirectoryService = phoneDirectoryService;
    }

    @GetMapping("/getAllPersons")
    public List<Person> returnAllPersons() {
        log.info("запрос на всех пользователей");
        return phoneDirectoryService.getPersonsList();
    }

    @GetMapping("/getAllInformationAllPersons")
    public List<String> returnAllInformationAllPersons() {
        log.info("запрос текстовой информации всех пользователей");
        return phoneDirectoryService.returnAllInformationAllPersons();
    }

    @GetMapping("/findEveryoneWhoLivesInTheCity")
    public List<FullNamePhoneNumb> returnPersonsWhoLivesInTheCity(@RequestParam("city") String city) {
        log.info("запрос на жителей города: {}", city);
        return phoneDirectoryService.findEveryoneWhoLivesInTheCityX(city);
    }

    @GetMapping(value = "/findPeopleWithoutPatronymic", produces = "application/xml")
    public List<FullNamePhoneNumbAddress> returnPeopleWithoutPatronymic() {
        log.info("запрос на людей без отчества");
        return phoneDirectoryService.findPeopleWithoutPatronymic();
    }

    @GetMapping("/findPeopleWithProfessionX")
    public List<Person> findPeopleWithProfessionXAndSortByCity(@RequestParam("profession") String profession) {
        log.info("запрос на людей с профессией: {} + сортировка по городу", profession);
        return phoneDirectoryService.findPeopleWithProfessionXAndSortByCity(profession);
    }

    @GetMapping("/findNPeopleWithTheSpecifiedProfession")
    public List<Person> findNPeopleWithTheSpecifiedProfession(
            @RequestParam("profession") String profession, int number) {
        log.info("Запрос на людей профессии {} в количестве {}", profession, number);
        return phoneDirectoryService.findNPeopleWithTheSpecifiedProfession(profession, number);
    }

    //Запрос в формате JSON и ответ в формате JSON
    @PostMapping(value = "/addNewPerson", produces = MediaType.APPLICATION_JSON_VALUE) //возвращает Json
    public Person addNewPerson(@RequestBody Person person) {
        log.info("запрос на добавление нового гражданина!");
        return phoneDirectoryService.addNewPerson(person);
    }

    /*  "phoneNumber": "+7-888-858-88-00",
        "firstName" : "Алексей",
        "LastName" : "Алексеев",
        "patronymic" : "",
        "cityOfResidence" :"Санкт-Петербург",
        "address" : "улица Гринькова, д.33, кв.76",
        "typeofActivity" : "Таксист"
    */


    @PostMapping(value = "/addNewPersonFormatXML", consumes = "application/xml", produces = "application/xml")
    public Person addANewPersonFormatXML(@RequestBody Person person) {
        return phoneDirectoryService.addNewPerson(person);
    }

    /*
    <person>
        <phoneNumber>+7-888-858-88-00"</phoneNumber>
        <firstName>Алексей</firstName>
        <LastName>Алексеев</LastName>
        <patronymic></patronymic>
        <cityOfResidence>Санкт-Петербург</cityOfResidence>
        <address>улица Гринькова, д.33, кв.76</address>
        <typeofActivity>Таксист</typeofActivity>
    </person>
    */

    @PutMapping(value = "/replaceUserData/{phoneNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person replaceUserData(@PathVariable("phoneNumber") Person newDataPerson,
                                  @RequestBody String numberPersonForReplace) {
        log.info("запрос на изменение данных о пользователе с номером: {}", numberPersonForReplace);
        //log.info("поле: {}; данные: {}", request.getField(), request.getData());
        return phoneDirectoryService.replaceUserData(newDataPerson, numberPersonForReplace);
    }

    /*
    {
    "phoneNumber" : "+7-111-111-11-11",
    "field" : "lastName",
    "data" : "Горький"
    }
    */
    @DeleteMapping("/deletePerson/{phoneNumber}")
    public boolean deletePerson(@PathVariable("phoneNumber") String phoneNumberDeletedPerson) {
        log.info("Запрос на удаление пользователя с номером телефона {}", phoneNumberDeletedPerson);
        return phoneDirectoryService.deletePerson(phoneNumberDeletedPerson);
    }

    //ОСТАВИЛ для примера
    @GetMapping(value = "/stringAsJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStringAsJson() {
        return "{\"message\":\"Привет, мир!\"}";
    }

    @GetMapping(value = "/responseEntityAsString", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getResponseEntityAsString() {
        return ResponseEntity.ok("Когда жизнь дарит тебе лимоны, преврати их в JSON!");
    }

}
