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

import static ru.phoneDirectoryTest.PersonsForPhoneDirectory.PHONE_DIRECTORY;

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
        Mockito.when(PhoneDirectory.returnAllInformationAllPersons(PHONE_DIRECTORY.getPersonList()))
                .thenReturn(PHONE_DIRECTORY.getPersonList().stream().map(Person::toString).toList());
        mockMvc.perform(MockMvcRequestBuilders.get("/getAllInformationAllPersons").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(
                        "ФИО: Иванов Николай Васильевич; " +
                                "Город: Москва; " +
                                "Адрес: улица Ромашковая, д.12; " +
                                "Профессия: Слесарь; " +
                                "Телефон: +7-111-111-11-11."));
    }
}
