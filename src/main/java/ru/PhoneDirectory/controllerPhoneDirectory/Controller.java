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
@RequestMapping("/persons")
//phoneDirectoryService - название сервиса, а не контролера
@Slf4j //логирование
public class Controller {

    private final PhoneDirectoryService phoneDirectoryService;

    @Autowired
    public Controller(PhoneDirectoryService phoneDirectoryService) {
        this.phoneDirectoryService = phoneDirectoryService;
    }

    @GetMapping("/all")
    public List<Person> returnAllPersons() {
        log.info("запрос на всех пользователей");
        return phoneDirectoryService.getAllPersons();
    }

//    @GetMapping("/getAllInformationAllPersons")
//    public List<String> returnAllInformationAllPersons() {
//        log.info("запрос текстовой информации всех пользователей");
//        return phoneDirectoryService.returnAllInformationAllPersons();
//    }

    @GetMapping("/findByCity/{city}")
    public List<FullNamePhoneNumb> returnPersonsWhoLivesInTheCityN(@PathVariable("city") String city) {
        log.info("запрос на жителей города: {}", city);
        return phoneDirectoryService.findEveryoneWhoLivesInTheCityN(city);
    }

    @GetMapping(value = "/withoutPatronymic", produces = "application/xml")
//byPatronimyc - если null, искать без очества
    public List<FullNamePhoneNumbAddress> returnPeopleWithoutPatronymic() { //todo может переделать под просто поиск по фамилии.
        log.info("запрос на людей без отчества");
        return phoneDirectoryService.findPeopleWithoutPatronymic();
    }

    @GetMapping("/findByProfession/{profession}")
    public List<Person> findPeopleWithProfessionXAndSortByCity(@PathVariable("profession") String profession) {
        log.info("запрос на людей с профессией: {} + сортировка по городу", profession);
        return phoneDirectoryService.findPeopleWithProfessionXAndSortByCity(profession);
    }

    @GetMapping("/findSomeByProfession/{profession}")
    public List<Person> findNPeopleWithTheSpecifiedProfession(
            @PathVariable("profession") String profession, @RequestParam("number") int number) {
        log.info("Запрос на людей профессии {} в количестве {}", profession, number);
        return phoneDirectoryService.findNPeopleWithTheSpecifiedProfession(profession, number);
    }

    @GetMapping("/call/{profession}")
    public List<Person> callAllPeopleWithProfessionX(@PathVariable("profession") String profession) {
        log.info("Запрос на прозвон людей с профессией {}", profession);
        return phoneDirectoryService.callAllPeopleWithProfessionX(profession);
    }

    //Запрос в формате JSON и ответ в формате JSON
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE) //возвращает Json
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


    @PostMapping(value = "/addNewPersonFormatXML", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
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

    @PutMapping(value = "/replace/{phoneNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person replaceUserData(@RequestBody Person newDataPerson,
                                  @PathVariable("phoneNumber") String numberPersonForReplace) {
        log.info("запрос на изменение данных о пользователе с номером: {}", numberPersonForReplace);
        return phoneDirectoryService.replaceUserData(newDataPerson, numberPersonForReplace);
    }

    /*
    {
    "phoneNumber" : "+7-111-111-11-11",
    "field" : "lastName",
    "data" : "Горький"
    }
    */

    @DeleteMapping("/delete/{phoneNumber}")
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
