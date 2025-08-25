package ru.PhoneDirectory.enums;

import lombok.Getter;
import ru.PhoneDirectory.Person;

import java.util.List;

@Getter
public enum Persons {

    NIKOLAY_IVANOV(new Person(
            "+7-111-111-11-11",
            "Николай",
            "Иванов",
            "Васильевич",
            "Москва",
            "улица Ромашковая, д.12",
            "Слесарь")),
    PETR_PETROV(new Person(
            "+7-222-222-22-22",
            "Петр",
            "Петров",
            "Петрович",
            "Санкт-Петербург",
            "улица Громова, д.6, кв.12",
            "Разработчик")),
    ILYA_ILYIYOV(new Person(
            "+7-333-333-33-33",
            "Илья",
            "Ильёв",
            "Ильич",
            "Санкт-Петербург",
            "улица Громова, д.12, кв.55",
            "Разработчик")),
    ALEKSANDR_ALEKSANDROV(new Person(
            "+7-444-444-44-44",
            "Александр",
            "Алекснадров",
            "Александрович",
            "Москва",
            "улица Ромашковая, д.12",
            "Слесарь")),
    IVANOV_IVAN(new Person(
            "+7-555-555-55-55",
            "Иван",
            "Иванов",
            "Иванович",
            "Екатеринбург",
            "улица Красноказарменная, д.33, кв.44",
            "Разработчик")),
    ARTEM_ARTEMOV(new Person(
            "+7-666-666-66-66",
            "Артем",
            "Артемов",
            "Артемович",
            "Екатеринбург",
            "улица Красноказарменная, д.12, кв.12",
            "Слесарь")),
    OLEG_OLEGOV(new Person(
            "+7-777-777-77-77",
            "Олег",
            "Олегов",
            "",
            "Москва",
            "улица Новохохловская, д.12",
            "Слесарь")),
    ALEKSEY_ALEKSEEV(new Person(
            "+7-888-888-88-88",
            "Алексей",
            "Алексеев",
            "",
            "Санкт-Петербург",
            "улица Гринькова, д.33, кв.76",
            "Таксист")),
    MAKSIM_MAKSIMOV(new Person(
            "+7-999-999-99-99",
            "Максим",
            "Максимов",
            "Максимович",
            "Белгород",
            "улица Королева, д.55, кв.22",
            "Стоматолог")),
    DENIS_DENISOV(new Person(
            "+7-000-000-00-00", //номер телефона пишется с кодом страны +7 expression
            "Денис",
            "Денисов",
            "",
            "Белгород",
            "улица Победы, д.1, кв.1",
            "Таксист"));
    private final Person person;

    Persons(Person person) {
        this.person = person;
    }

    //todo метод который вернет список персон
    public static List<Person> returnsAListOfPersons() {
        return List.of(NIKOLAY_IVANOV.getPerson(), PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson(),
                ALEKSANDR_ALEKSANDROV.getPerson(),
                IVANOV_IVAN.getPerson(), ARTEM_ARTEMOV.getPerson(), OLEG_OLEGOV.getPerson(), ALEKSEY_ALEKSEEV.getPerson(),
                MAKSIM_MAKSIMOV.getPerson(), DENIS_DENISOV.getPerson());
    }


}
