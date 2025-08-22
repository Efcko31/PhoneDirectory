package ru.PhoneDirectory;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.PhoneDirectory.dto.FullNamePhoneNumb;
import ru.PhoneDirectory.dto.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbAddressMapper;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbMapper;
import ru.PhoneDirectory.mapper.ReplacedUserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

@Data
@AllArgsConstructor
@Service
public class PhoneDirectoryService {
    private List<Person> personsList;

    public static List<Person> PERSON_LIST = new ArrayList<>(List.of(
            NIKOLAY_IVANOV.getPerson(), PETR_PETROV.getPerson(), ILYA_ILYIYOV.getPerson(), ALEKSANDR_ALEKSANDROV.getPerson(),
            IVANOV_IVAN.getPerson(), ARTEM_ARTEMOV.getPerson(), OLEG_OLEGOV.getPerson(), ALEKSEY_ALEKSEEV.getPerson(),
            MAKSIM_MAKSIMOV.getPerson(), DENIS_DENISOV.getPerson()));

    //public PhoneDirectoryService() {
        //this.personsList = PERSON_LIST;
    //}

    //todo надо убрать, что бы работать только с enum
    //todo метод на получение все в enum

    public static final String FULL_NAME_TYPE_ACTIVITY = "%s %s %s, %s\n";
    public static final String MASSAGE_BEGINNING_CALL = "Начат вызов. %s т.%s%n";

    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    public List<FullNamePhoneNumb> findEveryoneWhoLivesInTheCityX(
            String cityN) {
        return PERSON_LIST.stream()
                .filter(p -> cityN.equals(p.getCityOfResidence()))
                .map(FullNamePhoneNumbMapper.INSTANCE::toFullNamePhoneNumb)
                .collect(Collectors.toList());
    }

    //2)найти людей без отчества, и вернуть место их проживания, фио, номер телефона
    public List<FullNamePhoneNumbAddress> findPeopleWithoutPatronymic() {
        return PERSON_LIST.stream()
                .filter(p -> p.getPatronymic() == null || p.getPatronymic().isEmpty())
                .map(FullNamePhoneNumbAddressMapper.INSTANCE::toFullNamePhoneNumbAddress)
                .collect(Collectors.toList());
    }

    //3)найти людей с профессией x, и вернуть информацию о них отсротирваную по городу
    public List<Person> findPeopleWithProfessionXAndSortByCity(
            String profession) {
        return PERSON_LIST.stream()
                .filter(p -> p.getTypeofActivity().equalsIgnoreCase(profession))
                .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER
                        .compare(o1.getCityOfResidence(), o2.getCityOfResidence()))
                .collect(Collectors.toList());
    }

    //4)найти n людей с определенной профессией
    public List<Person> findNPeopleWithTheSpecifiedProfession(String profession, int n) {
        if (profession == null || n <= 0) return List.of();

        List<Person> listPeopleWithProfessionN = PERSON_LIST.stream()
                        .filter(s -> profession.equals(s.getTypeofActivity()))
                        .limit(n)
                        .toList();

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

        List<Person> subscribersToWhomCallWasMade = PERSON_LIST.stream()
                        .filter(p -> profession.equals(p.getTypeofActivity()))
                        .toList();

        subscribersToWhomCallWasMade.forEach(p ->
                System.out.printf(MASSAGE_BEGINNING_CALL, p.getFirstName(), p.getPhoneNumber()));
        System.out.println("-------------------------");
        return subscribersToWhomCallWasMade;
    }

    public List<String> returnAllInformationAllPersons() {
        return PERSON_LIST.stream()
                .map(Person::toString)
                .toList();
    }

    public Person addNewPerson(Person newPerson) {
        boolean isThereSuchPhoneNumber = PERSON_LIST.stream()
                .anyMatch(p -> checksUsersByPhoneNumber(newPerson.getPhoneNumber(), p));

        if (!isThereSuchPhoneNumber) {
            PERSON_LIST.add(newPerson);
            return newPerson;
        } else {
            throw new NoSuchElementException();
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
        throw new NoSuchElementException();
    }

    public Person replaceUserData(Person newDataPerson, String numberPersonForReplace) {
        Person replacedUser = findPersonByPhoneNumber(numberPersonForReplace);
        ReplacedUserMapper.INSTANCE.updatePersonFromDto(replacedUser, newDataPerson);
        PERSON_LIST.stream()
                .filter(p -> checksUsersByPhoneNumber(numberPersonForReplace, p))
                .map(p -> ReplacedUserMapper.INSTANCE.updatePersonFromDto(p, newDataPerson)
                ); //todo доделать
    }

//    public boolean replaceUserData(String phoneNumber, String field, String data) {
//        Person replacedUser = findPersonByPhoneNumber(phoneNumber);
//        //todo тут чето надо исправить типа объект класса персон с полями которые надо поменять, а остальные null
//        //todo и находим оригинал в листе
//        //todo и потом меняем поля в которых !null
//        if (replacedUser != null) {
//            switch (field.toLowerCase()) {
//                case "phonenumber":
//                    replacedUser.setPhoneNumber(data);
//                    break;
//                case "firstname":
//                    replacedUser.setFirstName(data);
//                    break;
//                case "lastname":
//                    replacedUser.setLastName(data);
//                    break;
//                case "patronymic":
//                    replacedUser.setPatronymic(data);
//                    break;
//                case "cityofresidence":
//                    replacedUser.setCityOfResidence(data);
//                    break;
//                case "address":
//                    replacedUser.setAddress(data);
//                    break;
//                case "typeofactivity":
//                    replacedUser.setTypeofActivity(data);
//                    break;
//            }
//            System.out.println("Данные изменены!");
//            System.out.println(replacedUser);
//            return true;
//        }
//        return false;
//    }

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
