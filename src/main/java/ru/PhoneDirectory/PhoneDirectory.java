package ru.PhoneDirectory;

import ru.PhoneDirectory.DTO.FullNamePhoneNumb;
import ru.PhoneDirectory.DTO.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbAddressMapper;
import ru.PhoneDirectory.mapper.FullNamePhoneNumbMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sun.tools.attach.VirtualMachine.list;

public class PhoneDirectory {

    public static final String FULL_NAME_TYPE_ACTIVITY = "%s %s %s, %s\n";
    public static final String MASSAGE_BEGINNING_CALL = "Начат вызов. %s т.%s%n";

    //1)найти всех людей проживающих в городе n, и вернуть их номер телефона и фио
    public static List<FullNamePhoneNumb> findEveryoneWhoLivesInTheCityX(
            String cityN, List<Person> phoneDirectory) {
        return Optional.ofNullable(cityN)
                .flatMap(city -> Optional.ofNullable(phoneDirectory)
                        .map(list -> list.stream()
                                .filter(Objects::nonNull)
                                .filter(p -> cityN.equals(p.getCityOfResidence()))
                                .map(FullNamePhoneNumbMapper.INSTANCE::toFullNamePhoneNumb)
                                .collect(Collectors.toList()))
                        .filter(list -> !list.isEmpty()))
                .orElseGet(List::of);
    }

    //2)найти людей без отчества, и вернуть место их проживания, фио, номер телефона
    public static List<FullNamePhoneNumbAddress> findPeopleWithoutPatronymic(List<Person> phoneDirectory) {
        return Optional.ofNullable(phoneDirectory)
                .map(list -> list.stream()
                        .filter(Objects::nonNull)
                        .filter(p -> p.getPatronymic() == null || p.getPatronymic().isEmpty())
                        .map(FullNamePhoneNumbAddressMapper.INSTANCE::toFullNamePhoneNumbAddress)
                        .collect(Collectors.toList()))
                .filter(list -> !list().isEmpty())
                .orElseGet(List::of);
    }

    //3)найти людей с профессией x, и вернуть информацию о них отсротирваную по городу
    public static List<Person> findPeopleWithProfessionXAndSortByCity(
            String profession, List<Person> phoneDirectory) {
        return Optional.ofNullable(phoneDirectory)
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
    public static List<Person> findNPeopleWithTheSpecifiedProfession(String profession, int n, List<Person> phoneDirectory) {
        if (profession == null || phoneDirectory == null || n <= 0) return List.of();

        List<Person> listPeopleWithProfessionN = Optional.of(phoneDirectory)
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
    public static List<Person> callAllPeopleWithProfessionX(String profession, List<Person> phoneDirectory) {
        if (profession == null) return List.of();

        List<Person> subscribersToWhomCallWasMade = Optional.ofNullable(phoneDirectory)
                .map(list -> list.stream()
                        .filter(p -> profession.equals(p.getTypeofActivity()))
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseGet(List::of);

        subscribersToWhomCallWasMade.forEach(PhoneDirectory::makeCall);
        System.out.println("-------------------------");
        return subscribersToWhomCallWasMade;
    }

    public static List<String> returnAllInformationAllPersons(List<Person> phoneDirectory) {
        return phoneDirectory.stream().map(Person::toString).toList();
    }

    public static String addNewPerson(Person newPerson, List<Person> phoneDirectory) {
        boolean isThereSuchPhoneNumber = phoneDirectory.stream()
                .anyMatch(p -> checksUsersByPhoneNumber(newPerson.getPhoneNumber(), p));

        if (!isThereSuchPhoneNumber) {
            phoneDirectory.add(newPerson);
            return "Добавлен новый гражданин!";
        } else {
            return "Пользователь с таким номером телефона уже есть!";
        }
    }

    public static boolean checksUsersByPhoneNumber(String phoneNumber, Person person) {
        return phoneNumber.substring(phoneNumber.indexOf("-"))
                .replaceAll("^[0-9]", "")
                .equals(person.getPhoneNumber()
                        .substring(person.getPhoneNumber().indexOf("-"))
                        .replaceAll("^[0-9]", ""));
    }

    public static void makeCall(Person person) {
        System.out.printf(MASSAGE_BEGINNING_CALL, person.getFirstName(), person.getPhoneNumber());
    }
}
