package ru.PhoneDirectory.PhoneDirectoryRepository;

import lombok.Getter;
import ru.PhoneDirectory.Person;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum PersonsForPhoneDirectory {

    nikolayIvanov(new Person(
            "+7-111-111-11-11",
            "Николай",
            "Иванов",
            "Васильевич",
            "Москва",
            "улица Ромашковая, д.12",
            "Слесарь")),
    petrPetrov(new Person(
            "+7-222-222-22-22",
            "Петр",
            "Петров",
            "Петрович",
            "Санкт-Петербург",
            "улица Громова, д.6, кв.12",
            "Разработчик")),
    ilyaIlyiyov(new Person(
            "+7-333-333-33-33",
            "Илья",
            "Ильёв",
            "Ильич",
            "Санкт-Петербург",
            "улица Громова, д.12, кв.55",
            "Разработчик")),
    aleksandrAleksandrov(new Person(
            "+7-444-444-44-44",
            "Александр",
            "Алекснадров",
            "Александрович",
            "Москва",
            "улица Ромашковая, д.12",
            "Слесарь")),
    ivanovIvan(new Person(
            "+7-555-555-55-55",
            "Иван",
            "Иванов",
            "Иванович",
            "Екатеринбург",
            "улица Красноказарменная, д.33, кв.44",
            "Разработчик")),
    artemArtemov(new Person(
            "+7-666-666-66-66",
            "Артем",
            "Артемов",
            "Артемович",
            "Екатеринбург",
            "улица Красноказарменная, д.12, кв.12",
            "Слесарь")),
    olegOlegov(new Person(
            "+7-777-777-77-77",
            "Олег",
            "Олегов",
            "",
            "Москва",
            "улица Новохохловская, д.12",
            "Слесарь")),
    alekseyAlekseev(new Person(
            "+7-888-888-88-88",
            "Алексей",
            "Алексеев",
            "",
            "Санкт-Петербург",
            "улица Гринькова, д.33, кв.76",
            "Таксист")),
    maksimMaksimov(new Person(
            "+7-999-999-99-99",
            "Максим",
            "Максимов",
            "Максимович",
            "Белгород",
            "улица Королева, д.55, кв.22",
            "Стоматолог")),
    denisDenisov(new Person(
            "+7-000-000-00-00", //номер телефона пишется с кодом страны +7 expression
            "Денис",
            "Денисов",
            "",
            "Белгород",
            "улица Победы, д.1, кв.1",
            "Таксист"));
    private final Person person;

    PersonsForPhoneDirectory(Person person) {
        this.person = person;
    }

    public static final List<Person> phoneDirectory = new ArrayList<>(List.of(
            nikolayIvanov.getPerson(), petrPetrov.getPerson(), ilyaIlyiyov.getPerson(), aleksandrAleksandrov.getPerson(),
            ivanovIvan.getPerson(), artemArtemov.getPerson(), olegOlegov.getPerson(), alekseyAlekseev.getPerson(),
            maksimMaksimov.getPerson(), denisDenisov.getPerson()));
}
