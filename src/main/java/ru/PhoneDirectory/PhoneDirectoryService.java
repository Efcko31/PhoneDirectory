package ru.PhoneDirectory;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.PhoneDirectory.dto.FullNamePhoneNumb;
import ru.PhoneDirectory.dto.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.enums.Persons;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbAddressMapper;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbMapper;
import ru.PhoneDirectory.mapper.ReplacedUserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Service
public class PhoneDirectoryService {

    private Persons enumPersons;
    private List<Person> personsList;

    public PhoneDirectoryService() {
        this.personsList = new ArrayList<>(Persons.returnsAListOfPersons());
    }

    public static final String FULL_NAME_TYPE_ACTIVITY = "%s %s %s, %s\n";
    public static final String MASSAGE_BEGINNING_CALL = "Начат вызов. %s т.%s%n";

    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    public List<FullNamePhoneNumb> findEveryoneWhoLivesInTheCityX(
            String cityN) {
        return personsList.stream()
                .filter(p -> cityN.equals(p.getCityOfResidence()))
                .map(FullNamePhoneNumbMapper.INSTANCE::toFullNamePhoneNumb)
                .collect(Collectors.toList());
    }

    //2)найти людей без отчества, и вернуть место их проживания, фио, номер телефона
    public List<FullNamePhoneNumbAddress> findPeopleWithoutPatronymic() {
        return personsList.stream()
                .filter(p -> p.getPatronymic() == null || p.getPatronymic().isEmpty())
                .map(FullNamePhoneNumbAddressMapper.INSTANCE::toFullNamePhoneNumbAddress)
                .collect(Collectors.toList());
    }

    //3)найти людей с профессией x, и вернуть информацию о них отсротирваную по городу
    public List<Person> findPeopleWithProfessionXAndSortByCity(
            String profession) {
        return personsList.stream()
                .filter(p -> p.getTypeofActivity().equalsIgnoreCase(profession))
                .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER
                        .compare(o1.getCityOfResidence(), o2.getCityOfResidence()))
                .collect(Collectors.toList());
    }

    //4)найти n людей с определенной профессией
    public List<Person> findNPeopleWithTheSpecifiedProfession(String profession, int n) {
        if (profession == null || n <= 0) return List.of();

        List<Person> listPeopleWithProfessionN = personsList.stream()
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

        List<Person> subscribersToWhomCallWasMade = personsList.stream()
                .filter(p -> profession.equals(p.getTypeofActivity()))
                .toList();

        subscribersToWhomCallWasMade.forEach(p ->
                System.out.printf(MASSAGE_BEGINNING_CALL, p.getFirstName(), p.getPhoneNumber()));
        System.out.println("-------------------------");
        return subscribersToWhomCallWasMade;
    }

    public List<String> returnAllInformationAllPersons() {
        return personsList.stream()
                .map(Person::toString)
                .toList();
    }

    public Person addNewPerson(Person newPerson) {
        boolean isThereSuchPhoneNumber = personsList.stream()
                .anyMatch(p -> checksUsersByPhoneNumber(newPerson.getPhoneNumber(), p));

        if (!isThereSuchPhoneNumber) {
            personsList.add(newPerson);
            return newPerson;
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean deletePerson(String phoneNumber) {
        return personsList.remove(findPersonByPhoneNumber(phoneNumber));
    }

    public Person findPersonByPhoneNumber(String phoneNumber) {
        for (Person p : personsList) {
            if (checksUsersByPhoneNumber(phoneNumber, p)) {
                return p;
            }
        }
        System.out.println("Пользователя с таким номером нет!"); //exception
        throw new NoSuchElementException();
    }

    public Person replaceUserData(Person newDataPerson, String numberPersonForReplace) {
        personsList.stream()
                .filter(p -> checksUsersByPhoneNumber(numberPersonForReplace, p))
                .forEach(p -> ReplacedUserMapper.INSTANCE.updatePersonFromDto(p, newDataPerson));
        return findPersonByPhoneNumber(numberPersonForReplace);
    }

    public boolean checksUsersByPhoneNumber(String phoneNumber, Person person) {
        return new StringBuilder(phoneNumber.replaceAll("\\D", ""))
                .reverse()
                .substring(0, 9)
                .equals(new StringBuilder(person.getPhoneNumber().replaceAll("\\D", ""))
                        .reverse()
                        .substring(0, 9));
    }

//    public void makeCall(Person person) {
//        System.out.printf(MASSAGE_BEGINNING_CALL, person.getFirstName(), person.getPhoneNumber());
//    }
}
