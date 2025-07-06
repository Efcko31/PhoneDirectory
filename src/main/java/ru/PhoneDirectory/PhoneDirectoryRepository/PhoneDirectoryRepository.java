package ru.PhoneDirectory.PhoneDirectoryRepository;

import ru.PhoneDirectory.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneDirectoryRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/my_first_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Bkmz1205!F";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addPerson(Person person) throws SQLException {
        String sql = "INSERT INTO phonedirectory (phone_number, first_name, last_name, patronymic, " +
                "city_of_resident, address, type_of_activity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, person.getPhoneNumber());
            stmt.setString(2, person.getFirstName());
            stmt.setString(3, person.getLastName());
            stmt.setString(4, person.getPatronymic());
            stmt.setString(5, person.getCityOfResidence());
            stmt.setString(6, person.getAddress());
            stmt.setString(7, person.getTypeofActivity());

            stmt.executeUpdate();
        }
    }

    public static Person findByPhoneNumber(String phoneNumber) throws SQLException {
        String sql = "SELECT * FROM phonedirectory WHERE phone_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNumber);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return new Person(
                        result.getString("phone_number"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("patronymic"),
                        result.getString("city_of_resident"),
                        result.getString("address"),
                        result.getString("type_of_activity"));
            }
            System.out.println("С данным номером телефона, человек не найден.");
            return null;
        }
    }

    public static List<Person> findEveryoneWhoLivesInTheCityXUsingSQL(String cityN) throws SQLException {
        List<Person> listPersonsWhoLiveInCityN = new ArrayList<>();
        String sql = "SELECT first_name, last_name, patronymic, phone_number" +
                " FROM phonedirectory WHERE city_of_resident = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cityN);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Person person = new Person(
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("patronymic"),
                        result.getString("phone_number")
                );
                listPersonsWhoLiveInCityN.add(person);
            }
        }
        return listPersonsWhoLiveInCityN;
    }

    public static List<Person> findPeopleWithoutPatronymic() throws SQLException {
        List<Person> listPeopleWithoutPatronymic = new ArrayList<>();
        String sql = "SELECT first_name, last_name, phone_number, city_of_resident, address" +
                " FROM phonedirectory WHERE patronymic = ?";

        try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "");
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                listPeopleWithoutPatronymic.add(new Person(
                        result.getString("city_of_resident"),
                        result.getString("address"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("phone_number")
                ));
            }
            return listPeopleWithoutPatronymic;
        }
    }

}
