package ru.PhoneDirectory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.PhoneDirectory.dto.FullNamePhoneNumb;
import ru.PhoneDirectory.dto.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbAddressMapper;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sun.tools.attach.VirtualMachine.list;
import static ru.PhoneDirectory.enums.Persons.ALEKSANDR_ALEKSANDROV;
import static ru.PhoneDirectory.enums.Persons.ALEKSEY_ALEKSEEV;
import static ru.PhoneDirectory.enums.Persons.ARTEM_ARTEMOV;
import static ru.PhoneDirectory.enums.Persons.DENIS_DENISOV;
import static ru.PhoneDirectory.enums.Persons.ILYA_ILYIYOV;
import static ru.PhoneDirectory.enums.Persons.IVANOV_IVAN;
import static ru.PhoneDirectory.enums.Persons.MAKSIM_MAKSIMOV;
import static ru.PhoneDirectory.enums.Persons.NIKOLAY_IVANOV;
import static ru.PhoneDirectory.enums.Persons.OLEG_OLEGOV;
import static ru.PhoneDirectory.enums.Persons.PETR_PETROV;
//todo не должно * в импортах

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class PhoneDirectoryService {

    public static List<Person> PERSON_LIST = new ArrayList<>(List.of(
            NIKOLAY_IVANOV.getPerson(), PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson(), ALEKSANDR_ALEKSANDROV.getPerson(),
            IVANOV_IVAN.getPerson(), ARTEM_ARTEMOV.getPerson(), OLEG_OLEGOV.getPerson(), ALEKSEY_ALEKSEEV.getPerson(),
            MAKSIM_MAKSIMOV.getPerson(), DENIS_DENISOV.getPerson()));

    //todo надо убрать, что бы работать только с enum
    //todo метод на получение все в enum

    public static final String FULL_NAME_TYPE_ACTIVITY = "%s %s %s, %s\n";
    public static final String MASSAGE_BEGINNING_CALL = "Начат вызов. %s т.%s%n";

    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    public List<FullNamePhoneNumb> findEveryoneWhoLivesInTheCityX(
            String cityN) {
        return Optional.ofNullable(cityN)
                .flatMap(city -> Optional.ofNullable(PERSON_LIST)
                        .map(list -> list.stream()
                                .filter(Objects::nonNull)
                                .filter(p -> cityN.equals(p.getCityOfResidence()))
                                .map(FullNamePhoneNumbMapper.INSTANCE::toFullNamePhoneNumb)
                                .collect(Collectors.toList()))
                        .filter(list -> !list.isEmpty()))
                .orElseGet(List::of);
    }

    //2)найти людей без отчества, и вернуть место их проживания, фио, номер телефона
    public List<FullNamePhoneNumbAddress> findPeopleWithoutPatronymic() {
        return Optional.ofNullable(PERSON_LIST)
                .map(list -> list.stream()
                        .filter(Objects::nonNull)
                        .filter(p -> p.getPatronymic() == null || p.getPatronymic().isEmpty())
                        .map(FullNamePhoneNumbAddressMapper.INSTANCE::toFullNamePhoneNumbAddress)
                        .collect(Collectors.toList()))
                .filter(list -> !list().isEmpty())
                .orElseGet(List::of);
    }

    //3)найти людей с профессией x, и вернуть информацию о них отсротирваную по городу
    public List<Person> findPeopleWithProfessionXAndSortByCity(
            String profession) {
        return Optional.ofNullable(PERSON_LIST)
                .map(list -> list.stream()
                        .filter(Objects::nonNull)
                        .filter(p -> p.getTypeofActivity().equalsIgnoreCase(profession))
                        .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER
                                .compare(o1.getCityOfResidence(), o2.getCityOfResidence()))
                        .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .orElseGet(List::of);
    }

    //4)найти n людей с определенной профессией
    public List<Person> findNPeopleWithTheSpecifiedProfession(String profession, int n) {
        if (profession == null || PERSON_LIST == null || n <= 0) return List.of();

        List<Person> listPeopleWithProfessionN = Optional.of(PERSON_LIST)
                .map(list -> list.stream()
                        .filter(Objects::nonNull)
                        .filter(s -> profession.equals(s.getTypeofActivity()))
                        .limit(n)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseGet(List::of);

        listPeopleWithProfessionN.forEach(p -> System.out.printf(FULL_NAME_TYPE_ACTIVITY,
                p.getLastName(),
                p.getFirstName(),
                p.getPatronymic(),
                p.getTypeofActivity()));
        return listPeopleWithProfessionN;
    }

    //5)осуществить прозвон всех людей с профессией x, с уточнением актуальности информации
    public List<Person> callAllPeopleWithProfessionX(String profession) {
        if (profession == null) return List.of();

        List<Person> subscribersToWhomCallWasMade = Optional.ofNullable(PERSON_LIST)
                .map(list -> list.stream()
                        .filter(p -> profession.equals(p.getTypeofActivity()))
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseGet(List::of);

        subscribersToWhomCallWasMade.forEach(p ->
                System.out.printf(MASSAGE_BEGINNING_CALL, p.getFirstName(), p.getPhoneNumber()));
        System.out.println("-------------------------");
        return subscribersToWhomCallWasMade;
    }

    public List<String> returnAllInformationAllPersons() {
        return PERSON_LIST.stream().map(Person::toString).toList();
    }

    public String addNewPerson(Person newPerson) {
        boolean isThereSuchPhoneNumber = PERSON_LIST.stream()
                .anyMatch(p -> checksUsersByPhoneNumber(newPerson.getPhoneNumber(), p));

        if (!isThereSuchPhoneNumber) {
            PERSON_LIST.add(newPerson);
            return "Добавлен новый гражданин!";
        } else {
            return "Пользователь с таким номером телефона уже есть!";
        }
    }

    public boolean deletePerson(String phoneNumber) {
        return PERSON_LIST.remove(findPersonByPhoneNumber(phoneNumber));
    }

    public Person findPersonByPhoneNumber(String phoneNumber) {
        for (Person p : PERSON_LIST) {
            if (checksUsersByPhoneNumber(phoneNumber, p)) {
                return p;
            }
        }
        System.out.println("Пользователя с таким номером нет!"); //exception
        return null;
    }

    public boolean replaceUserData(String phoneNumber, String field, String data) {
        Person replacedUser = findPersonByPhoneNumber(phoneNumber);
        if (replacedUser != null) {
            switch (field.toLowerCase()) {
                case "phonenumber":
                    replacedUser.setPhoneNumber(data);
                    break;
                case "firstname":
                    replacedUser.setFirstName(data);
                    break;
                case "lastname":
                    replacedUser.setLastName(data);
                    break;
                case "patronymic":
                    replacedUser.setPatronymic(data);
                    break;
                case "cityofresidence":
                    replacedUser.setCityOfResidence(data);
                    break;
                case "address":
                    replacedUser.setAddress(data);
                    break;
                case "typeofactivity":
                    replacedUser.setTypeofActivity(data);
                    break;
            }
            System.out.println("Данные изменены!");
            System.out.println(replacedUser);
            return true;
        }
        return false;
    }

    private boolean checksUsersByPhoneNumber(String phoneNumber, Person person) {
        return phoneNumber.substring(phoneNumber.indexOf("-"))
                .replaceAll("^[0-9]", "")
                .equals(person.getPhoneNumber()
                        .substring(person.getPhoneNumber().indexOf("-"))
                        .replaceAll("^[0-9]", ""));
    }

//    public void makeCall(Person person) {
//        System.out.printf(MASSAGE_BEGINNING_CALL, person.getFirstName(), person.getPhoneNumber());
//    }
}
