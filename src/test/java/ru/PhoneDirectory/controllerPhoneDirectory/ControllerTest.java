package ru.PhoneDirectory.controllerPhoneDirectory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.PhoneDirectory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.PhoneDirectory.Tests.Persons.PERSONS;
import static ru.PhoneDirectory.Tests.Persons.*;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PhoneDirectory phoneDirectory;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersonsTest() throws Exception {
        PhoneDirectory testPersons = new PhoneDirectory(new ArrayList<>(List.of(
                nikolayIvanov.getPerson(), petrPetrov.getPerson(), ilyaIlyiyov.getPerson(), aleksandrAleksandrov.getPerson(),
                ivanovIvan.getPerson(), artemArtemov.getPerson(), olegOlegov.getPerson(), alekseyAlekseev.getPerson(),
                maksimMaksimov.getPerson(), denisDenisov.getPerson())));

        when(phoneDirectory.returnAllInformationAllPersons())
                .thenReturn(testPersons.returnAllInformationAllPersons());

        mockMvc.perform(MockMvcRequestBuilders.get("/getAllInformationAllPersons"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]")
                        .value(testPersons.getPersonList().getFirst().toString()));
//        "ФИО: Иванов Николай Васильевич; " +
//                "Город: Москва; " +
//                "Адрес: улица Ромашковая, д.12; " +
//                "Профессия: Слесарь; " +
//                "Телефон: +7-111-111-11-11.")
    }
}
