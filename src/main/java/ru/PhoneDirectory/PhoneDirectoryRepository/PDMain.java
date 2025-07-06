package ru.PhoneDirectory.PhoneDirectoryRepository;

import java.sql.SQLException;

import static ru.PhoneDirectory.PhoneDirectoryRepository.PersonsForPhoneDirectory.phoneDirectory;

public class PDMain {
    public static void main(String[] args) {
        PhoneDirectoryRepository repository = new PhoneDirectoryRepository();
        phoneDirectory.forEach(p -> {
            try {
                repository.addPerson(p);
                System.out.println(p.getFirstName() + p.getLastName() + " - добывлен в БД");
            } catch (SQLException e) {
                System.out.println("Ошибка: " + e.getMessage());
                ;
            }
        });
    }
}
