package ru.PhoneDirectory.controllerPhoneDirectory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectory;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.PhoneDirectory.Tests.Persons.*;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PhoneDirectory phoneDirectory;

    // @InjectMocks
    // private Controller controller;

    @BeforeEach
    void setup() {
        Controller controller = new Controller(phoneDirectory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //MockitoAnnotations.openMocks(this);
    }

    public PhoneDirectory testPersons = new PhoneDirectory(List.of(
            nikolayIvanov.getPerson(), petrPetrov.getPerson(), ilyaIlyiyov.getPerson(), aleksandrAleksandrov.getPerson(),
            ivanovIvan.getPerson(), artemArtemov.getPerson(), olegOlegov.getPerson(), alekseyAlekseev.getPerson(),
            maksimMaksimov.getPerson(), denisDenisov.getPerson()));

    @Test
    void getAllPersonsTest() throws Exception {
        when(phoneDirectory.returnAllInformationAllPersons())
                .thenReturn(testPersons.returnAllInformationAllPersons());

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneDirectory/getAllInformationAllPersons")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]")
                        .value(testPersons.getPersonList().getFirst().toString()));
    }

    @Test
    void addsANewPersonTest() throws Exception {
//        Person newPerson = new Person(
//        "+7-888-858-88-00",
//                "Алексей",
//                "Алексеев",
//                "",
//                "Санкт-Петербург",
//                "улица Гринькова, д.33, кв.76",
//                "Таксист");

        when(phoneDirectory.addNewPerson(any(Person.class))).thenReturn("Добавлен новый гражданин!");

        mockMvc.perform(MockMvcRequestBuilders.post("/phoneDirectory/addNewPerson")
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
}
