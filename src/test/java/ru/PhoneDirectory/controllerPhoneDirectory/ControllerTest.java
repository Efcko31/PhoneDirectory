package ru.PhoneDirectory.controllerPhoneDirectory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        mockMvc.perform(get("/phoneDirectory/getAllInformationAllPersons")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]")
                        .value(testPersons.getPersonsList().getFirst().toString()));
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

        mockMvc.perform(post("/phoneDirectory/addNewPerson")
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
                .andExpect(content().string("Добавлен новый гражданин!"));
    }

    @Test
    void deletePersonByPhoneNumberTest() throws Exception {
        when(phoneDirectoryService.deletePerson("+7-111-111-11-11"))
                .thenReturn(true);

        mockMvc.perform(delete("/phoneDirectory/deletePerson/+7-111-111-11-11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(phoneDirectoryService.deletePerson("+7-111-000-11-11")).
                thenReturn(false);
        mockMvc.perform(delete("/phoneDirectory/deletePerson/+7-111-000-11-11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void replaceUserDataTest() throws Exception {
        Person updatePerson = new Person(
                "+7-999-999-99-99",
                "Максим",
                "Максимов",
                "Максимович",
                "Белгород",
                "Пушкенский переулок д.1",
                "Стоматолог");

        when(phoneDirectoryService.replaceUserData(new Person(
                        null,
                        null,
                        "Пушкин",
                        null,
                        null,
                        "Пушкенский переулок д.1",
                        null
                ), "89999999999")
                ).thenReturn(updatePerson);

        mockMvc.perform(put("/phoneDirectory/replaceUserData/+7-999-999-99-99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"field\" : \"lastName\",\n" +
                                "\"data\" : \"Грачевский\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
