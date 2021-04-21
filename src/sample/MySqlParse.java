package sample;

import java.sql.*;

public class MySqlParse {
    private  String connectionUrl;
    private  String userName ;
    private  String password ;
    private Statement statement;

    MySqlParse() {
        try {
            PropertiesParse propertiesParse = new PropertiesParse();
            var settings = propertiesParse.bdSettings();
            this.connectionUrl = settings[0];
            this.userName = settings[1];
            this.password = settings[2];
            Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            System.out.println("Подключение прошло успешно!");
            this.statement = (Statement) connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAll() throws SQLException {
        return this.statement.executeQuery("select * from students");
    }

    public ResultSet searchRecord(int id) throws SQLException {
        return this.statement.executeQuery("select * from students where id in(" + id + ");");
    }

    public void addNewRecord(String[] strings) throws SQLException {
        this.statement.executeUpdate("INSERT INTO students (name, surname, patronymic, school, clas , age)" +
                " VALUES ('" + strings[0] + "','" + strings[1] + "', '" + strings[2] + "', '" + strings[3] + "', " +
                "'" + strings[4] + "','" + strings[5] + "')");
    }

    public void addNewRecord(Student student) throws SQLException {
        statement.executeUpdate("INSERT INTO students (name, surname, patronymic, school, clas , age)" +
                " VALUES ('" + student.name + "','" + student.surname + "', '" + student.patronymic + "', '" + student.school + "', " +
                "'" + student.clas + "','" + student.age + "')");
    }

    public void updateRecord(int id) throws SQLException {
        var strings = Main.setValueStudent();
        statement.executeUpdate("update students set name = '" + strings[0] + "', surname = '" + strings[1] + "', patronymic = '" + strings[2] + "', school = '" + strings[3] + "', clas = '" + strings[4] + "', age = '" + strings[5] + "' where id = " + id + ";");
    }
    public void updateRecord(int id, Student student) throws SQLException {
        statement.executeUpdate("update students set name = '" + student.getName() + "', surname = '" + student.getSurname() + "', patronymic = '" + student.getPatronymic() + "', school = '" + student.getSchool() + "', clas = '" + student.getClas() + "', age = '" + student.getAge() + "' where id = " + id + ";");
    }


    public void deleteRecord(int id) throws SQLException {
        statement.executeUpdate("delete from students where id in(" + id + ");");
    }


    public ResultSet workDataBase(int action) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                switch (action) {
                    case 1 -> {
                        return getAll();
                    }
                    case 2 -> {
                        System.out.println("Введите Id записи");
                        var id = Main.getNum();
                        return searchRecord(id);
                    }
                    case 3 -> {
                        var strings = Main.setValueStudent();
                        addNewRecord(strings);
                    }
                    case 4 -> {
                        System.out.println("Введите Id записи");
                        var id = Main.getNum();
                        updateRecord(id);
                    }
                    case 5 -> {
                        System.out.println("Введите Id записи");
                        var id = Main.getNum();
                        deleteRecord(id);
                    }
                }
            } catch (Exception e) {
                var prop = new PropertiesParse();
                System.out.println(prop.error());
            }
        } catch (Exception e) {
            var prop = new PropertiesParse();
            System.out.println(prop.errorDriver());
        }
        return null;
    }

}
