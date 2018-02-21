package ru.feeleen.schoolSocialNetwork.DAL.MariaDB;

import ru.feeleen.schoolSocialNetwork.DAL.abstruct.INetWorkDAL;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MariaDBDAL implements INetWorkDAL {
    Properties properties = new Properties();

    public MariaDBDAL() {
        properties.setProperty("url", "jdbc:mariadb://localhost/schoolnetwork?useUnicode=yes&characterEncoding=UTF-8");
        properties.setProperty("jdbc.driver", "org.mariadb.jdbc.Driver");
        properties.setProperty("user", "root");
        properties.setProperty("password", "2017");

        try {
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found.");
            e.printStackTrace();
        }
    }


    @Override
    public PersonDTO getPerson(UUID id) {
        return null;
    }

    @Override
    public Iterable<PersonDTO> getAllPersons() {
        List<PersonDTO> personsList = null;
        // String SqlQuery = "SELECT UUID, FirstName, MiddleName, SecondName from persons";
        String SqlQuery = "SELECT persons.uuid, persons.firstName, persons.middleName, persons.secondName, schools.name, years.attendDate, years.endDate from persons LEFT JOIN schools ON persons.uuid=schools.uuid  LEFT JOIN years ON schools.uuid=years.uuid";

        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties)) {
            try (PreparedStatement st = connection.prepareStatement(SqlQuery)) {
                st.executeQuery();

                try (ResultSet rs = st.getResultSet()) {
                    personsList = new ArrayList<>();
                    while (rs.next()) {
                        PersonDTO personDTO = new PersonDTO();
                        personDTO.id = UUID.fromString(rs.getString(1));
                        personDTO.firstName = (rs.getString(2));
                        personDTO.middleName = (rs.getString(3));
                        personDTO.secondName = (rs.getString(4));
                        if (rs.getString(5) != null) {
                            personDTO.school = (rs.getString(5));
                            personDTO.attendDate = new GregorianCalendar(rs.getInt(6), 1, 1);
                            personDTO.endDate = new GregorianCalendar(rs.getInt(7), 1, 1);
                        }

                        personsList.add(personDTO);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection problem.");
            e.printStackTrace();
        }


        return personsList;
    }

    @Override
    public boolean update(PersonDTO person) {
        String SqlQuery = "UPDATE persons SET firstName = ?, middleName = ?, secondName = ? WHERE uuid = ?";
        String SqlQuery2 = "UPDATE schools SET name = ? WHERE uuid = ?";
        String SqlQuery3 = "UPDATE years SET attendDate = ?, endDate = ? WHERE uuid = ?";
//        String SqlQuery2 = "UPDATE schools SET name = '" + person.school + "', WHERE uuid = '" + person.id + "'";
//        String SqlQuery3 = "UPDATE years SET attendDate = ?, endDate = ? WHERE uuid = ?";
        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties)) {
            try (PreparedStatement st = connection.prepareStatement(SqlQuery)) {
                st.setString(1, person.firstName);
                st.setString(2, person.middleName);
                st.setString(3, person.secondName);
                st.setString(4, person.id.toString());
                st.executeQuery();
            }
            if (person.school != null && !person.equals("")) {
                try (PreparedStatement st = connection.prepareStatement(SqlQuery2)) {
                    st.setString(1, person.school);
                    st.setString(2, person.id.toString());
                    st.executeQuery();
                }

                try (PreparedStatement st = connection.prepareStatement(SqlQuery3)) {
                    st.setInt(1, person.attendDate.get(Calendar.YEAR));
                    st.setInt(2, person.endDate.get(Calendar.YEAR));
                    st.setString(3, person.id.toString());
                    st.executeQuery();
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection problem.");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deletePerson(UUID id) {
//        String SqlQuery = "DELETE FROM persons WHERE uuid='" + id + "'";
//        String SqlQuery2 = "DELETE FROM persons WHERE uuid='" + id + "'";
//        String SqlQuery3 = "DELETE FROM persons WHERE uuid='" + id + "'";
        String SqlQuery = "DELETE FROM persons WHERE uuid=?";
        String SqlQuery2 = "DELETE FROM schools WHERE uuid=?";
        String SqlQuery3 = "DELETE FROM years WHERE uuid=?";
        //String SqlQueryMain = "DELETE persons.uuid, persons.firstName, persons.middleName, persons.secondName, schools.name, years.attendDate, years.endDate from persons LEFT JOIN schools ON persons.uuid=schools.uuid  LEFT JOIN years ON schools.uuid=years.uuid";
        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties)) {
            try (PreparedStatement st = connection.prepareStatement(SqlQuery)) {
                st.setString(1, id.toString());
                st.executeQuery();
            }
            try (PreparedStatement st = connection.prepareStatement(SqlQuery2)) {
                st.setString(1, id.toString());
                st.executeQuery();
            }
            try (PreparedStatement st = connection.prepareStatement(SqlQuery3)) {
                st.setString(1, id.toString());
                st.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println("Connection problem.");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean create(PersonDTO person) {
        String SqlQuery = "INSERT INTO persons VALUES ('" + person.id + "' ,'" + person.firstName + "', '" + person.middleName + "', '" + person.secondName + "')";
        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties)) {
            try (PreparedStatement st = connection.prepareStatement(SqlQuery)) {
                st.executeQuery();
            }
            if (person.school != null && !person.school.isEmpty()) {
                String SqlQuery2 = "INSERT INTO schools VALUES ('" + person.id + "' ,'" + person.school + "')";

                try (PreparedStatement st = connection.prepareStatement(SqlQuery2)) {
                    st.executeQuery();
                }
                String SqlQuery3 = "INSERT INTO years VALUES ('" + person.id + "' ," + person.attendDate.get(Calendar.YEAR) + ", " + person.endDate.get(Calendar.YEAR) + ")";

                try (PreparedStatement st = connection.prepareStatement(SqlQuery3)) {
                    st.executeQuery();
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection problem.");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public
    boolean save() throws IOException {
        return false;
    }

    @Override
    public Map<String, Integer> getSchoolsWithRating() {
        Map<String, Integer> result = null;

        String SqlQuery = "SELECT schools.name from schools LEFT JOIN persons on schools.uuid = persons.uuid  ";

        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties)) {
            try (PreparedStatement st = connection.prepareStatement(SqlQuery)) {
                st.executeQuery();

                result = new HashMap<String, Integer>();

                try (ResultSet rs = st.getResultSet()) {
                    while (rs.next()) {
                        Integer val = result.get(rs.getString(1));
                        if (val != null) {
                            result.put(rs.getString(1), val + 1);
                        } else {
                            result.put(rs.getString(1), 1);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection problem.");
            e.printStackTrace();
        }

        return result;
    }

    public void checkExist() {

    }

}
