package ru.PhoneDirectory.controllerPhoneDirectory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.PhoneDirectory.dto.UpdateRequest;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectoryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.PhoneDirectory.enums.Persons.*;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneDirectoryService phoneDirectoryService;


    public PhoneDirectoryService testPersons = new PhoneDirectoryService(List.of(
            NIKOLAY_IVANOV.getPerson(), PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson(), ALEKSANDR_ALEKSANDROV.getPerson(),
            IVANOV_IVAN.getPerson(), ARTEM_ARTEMOV.getPerson(), OLEG_OLEGOV.getPerson(), ALEKSEY_ALEKSEEV.getPerson(),
            MAKSIM_MAKSIMOV.getPerson(), DENIS_DENISOV.getPerson()));

    @Test
    void getAllPersonsTest() throws Exception {
        when(phoneDirectoryService.returnAllInformationAllPersons())
                .thenReturn(testPersons.returnAllInformationAllPersons());

        mockMvc.perform(get("/phoneDirectory/getAllInformationAllPersons")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]")
                        .value(testPersons.getPersonList().getFirst().toString()));
    }

    @Test
    void addsANewPersonTest() throws Exception {

        when(phoneDirectoryService.addNewPerson(any(Person.class))).thenReturn("Добавлен новый гражданин!");

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

        when(phoneDirectoryService.deletePerson("+7-111-111-11-11")).thenReturn(true);//todo как будто бы что-то лишнее

        mockMvc.perform(delete("/phoneDirectory/deletePerson/+7-111-111-11-11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(phoneDirectoryService.deletePerson("+7-111-000-11-11")).thenReturn(false);//todo как будто бы что-то лишнее
        mockMvc.perform(delete("/phoneDirectory/deletePerson/+7-111-000-11-11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void replaceUserDataTest() throws Exception {
        UpdateRequest requestTest = new UpdateRequest("lastName", "Грачевский");

        when(phoneDirectoryService.replaceUserData("+7-999-999-99-99",
                requestTest.getField(), requestTest.getData())).thenReturn(true);

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
