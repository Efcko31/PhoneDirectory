package ru.PhoneDirectory.controllerPhoneDirectory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectoryService;
import ru.PhoneDirectory.dto.FullNamePhoneNumb;
import ru.PhoneDirectory.dto.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbAddressMapper;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbMapper;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.PhoneDirectory.enums.Persons.*;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneDirectoryService phoneDirectoryService;

    public PhoneDirectoryService testPersons = new PhoneDirectoryService();

    @Test
    void getAllPersonsTest() throws Exception {
        when(phoneDirectoryService.returnAllInformationAllPersons())
                .thenReturn(testPersons.returnAllInformationAllPersons());

        mockMvc.perform(get("/phoneDirectoryService/getAllInformationAllPersons")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]")
                        .value(testPersons.getPersonsList().getFirst().toString()))
                .andExpect(jsonPath("$[-1]")
                        .value(testPersons.getPersonsList().getLast().toString()));
    }

    @Test
    void addsANewPersonTest() throws Exception {
        Person newPersonTest = new Person(
                "+7-888-858-88-00",
                "Алексей",
                "Алексеев",
                "",
                "Санкт-Петербург",
                "улица Гринькова, д.33, кв.76",
                "Таксист"
        );

        when(phoneDirectoryService.addNewPerson(any(Person.class)))
                .thenReturn(newPersonTest);

        mockMvc.perform(post("/phoneDirectoryService/addNewPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"phoneNumber\" : \"+7-888-858-88-00\",\n" +
                                "\"firstName\" : \"Алексей\",\n" +
                                "\"lastName\" : \"Алексеев\",\n" +
                                "\"patronymic\" : \"\",\n" +
                                "\"cityOfResidence\" : \"Санкт-Петербург\",\n" +
                                "\"address\" : \"улица Гринькова, д.33, кв.76\",\n" +
                                "\"typeofActivity\" : \"Таксист\"" +
                                "}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("+7-888-858-88-00"))
                .andExpect(jsonPath("$.address").value("улица Гринькова, д.33, кв.76"))
                .andExpect(jsonPath("$.lastName").value("Алексеев"));
    }

    @Test
    void deletePersonByPhoneNumberTest() throws Exception {
        when(phoneDirectoryService.deletePerson("+7-111-111-11-11"))
                .thenReturn(true);

        mockMvc.perform(delete("/phoneDirectoryService/deletePerson/+7-111-111-11-11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(phoneDirectoryService.deletePerson(eq("+7-111-000-11-11"))).
                thenReturn(false);
        mockMvc.perform(delete("/phoneDirectoryService/deletePerson/+7-111-000-11-11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void replaceUserDataTest() throws Exception {
        Person updatedPerson = new Person(
                "+7-999-999-99-99",
                "Максим",
                "Пушкин",
                "Максимович",
                "Белгород",
                "улица Королева д.1",
                "Стоматолог"
        );

        when(phoneDirectoryService.replaceUserData(any(Person.class), eq("89999999999"))
        ).thenReturn(updatedPerson);

        mockMvc.perform(put("/phoneDirectoryService/replaceUserData/89999999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"lastName\" : \"Пушкин\",\n" +
                                "\"address\" : \"улица Королева д.1\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("+7-999-999-99-99"))
                .andExpect(jsonPath("$.address").value("улица Королева д.1"))
                .andExpect(jsonPath("$.lastName").value("Пушкин"));
    }

    @Test
    void callAllPeopleWithProfessionXTest() throws Exception {
        String professionToRequest = "разработчик";
        List<Person> peopleWithTheRequestedProfession = List.of(PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson(),
                IVANOV_IVAN.getPerson());

        when(phoneDirectoryService.callAllPeopleWithProfessionX(eq(professionToRequest)))
                .thenReturn(peopleWithTheRequestedProfession);

        mockMvc.perform(get("/phoneDirectoryService/callAllPeopleWithProfessionX")
                        .param("profession", professionToRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName")
                        .value(peopleWithTheRequestedProfession.getFirst().getFirstName()));
    }

    @Test
    void findNPeopleWithTheSpecifiedProfessionTest() throws Exception {
        String professionToRequest = "разработчик";
        List<Person> peopleWithTheRequestedProfessionAndeNumber = List.of(PETR_PETROV.getPerson(),
                ILYA_ILYIYOV.getPerson());

        when(phoneDirectoryService.findNPeopleWithTheSpecifiedProfession(professionToRequest, 2))
                .thenReturn(peopleWithTheRequestedProfessionAndeNumber);

        mockMvc.perform(get("/phoneDirectoryService/findNPeopleWithTheSpecifiedProfession")
                        .param("profession", professionToRequest)
                        .param("number", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].lastName")
                        .value(peopleWithTheRequestedProfessionAndeNumber.getLast().getLastName()));

    }

    @Test
    void returnPersonsWhoLivesInTheCityXTest() throws Exception {
        List<FullNamePhoneNumb> peopleWithTheRequestedWhoLivesInTheCity = Stream.of(NIKOLAY_IVANOV.getPerson(),
                        ALEKSANDR_ALEKSANDROV.getPerson(), OLEG_OLEGOV.getPerson())
                .map(FullNamePhoneNumbMapper.INSTANCE::toFullNamePhoneNumb)
                .toList();

        when(phoneDirectoryService.findEveryoneWhoLivesInTheCityN("МосквА"))
                .thenReturn(peopleWithTheRequestedWhoLivesInTheCity);

        mockMvc.perform(get("/phoneDirectoryService/findEveryoneWhoLivesInTheCityN")
                        .param("cityN", "МосквА")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].phoneNumber")
                        .value(peopleWithTheRequestedWhoLivesInTheCity.getFirst().getPhoneNumber()));

    }

    @Test
    void findPeopleWithoutPatronymicTest() throws Exception {
        List<FullNamePhoneNumbAddress> personListWithoutPatronymic = Stream.of(OLEG_OLEGOV.getPerson(),
                        ALEKSEY_ALEKSEEV.getPerson(), DENIS_DENISOV.getPerson())
                .map(FullNamePhoneNumbAddressMapper.INSTANCE::toFullNamePhoneNumbAddress)
                .toList();

        when(phoneDirectoryService.findPeopleWithoutPatronymic()).thenReturn(personListWithoutPatronymic);

        mockMvc.perform(get("/phoneDirectoryService/findPeopleWithoutPatronymic")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(xpath("/List/item[1]/lastName/text()")
                        .string(personListWithoutPatronymic.getFirst().getLastName()));
    }

    @Test
    void findPeopleWithProfessionXAndSortByCityTest() throws Exception {
        List<Person> ListPeopleWithProfessionXAndSortByCity = List.of(DENIS_DENISOV.getPerson(),
                ALEKSEY_ALEKSEEV.getPerson());

        when(phoneDirectoryService.findPeopleWithProfessionXAndSortByCity("ТаКсист"))
                .thenReturn(ListPeopleWithProfessionXAndSortByCity);

        mockMvc.perform(get("/phoneDirectoryService/findPeopleWithProfessionX")
                        .param("profession", "ТаКсист")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName")
                        .value(ListPeopleWithProfessionXAndSortByCity.getFirst().getLastName()));
    }
}
