package ru.PhoneDirectory.phoneDirectoryRepository;

import ru.PhoneDirectory.PhoneDirectoryService;

import java.sql.SQLException;

public class PDMain {

    public static final PhoneDirectoryService PHONE_DIRECTORY = new PhoneDirectoryService();

    public static void main(String[] args) {
        PhoneDirectoryRepository repository = new PhoneDirectoryRepository();
        PHONE_DIRECTORY.getPersonsList().forEach(p -> {
                    try {
                        repository.addPerson(p);
                        System.out.println(p.getFirstName() + p.getLastName() + " - добавлен в БД");
                    } catch (SQLException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }
        );
    }
}
