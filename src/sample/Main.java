package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    private static String setValue() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.next();
            if (str.length() > 0) {
                return str;
            } else {
                System.out.println("Необходимо ввести значение!");
            }
        }
    }

    protected static String[] setValueStudent() {
        String[] strings = new String[6];
        System.out.println("Введите значения для ученика:");
        System.out.println("Введите имя:");
        strings[0] = setValue();
        System.out.println("Введите фамилию:");
        strings[1] = setValue();
        System.out.println("Введите отчество:");
        strings[2] = setValue();
        System.out.println("Введите школу:");
        strings[3] = setValue();
        System.out.println("Введите класс:");
        strings[4] = setValue();
        System.out.println("Введите возраст:");
        strings[5] = getNum() + "";
        return strings;
    }

    private static Student setNewStudent(int size) {
        var strings = setValueStudent();
        return new Student(size + 1, strings[0], strings[1], strings[2],
                strings[3], strings[4], strings[5]);

    }

    private static void changeStudent(String filePath) {
        var sax = new SAXParse();
        var students = sax.readerSaxDocument(filePath);
        System.out.println("Введите id ученика:");
        var searchId = getNum();
        var strings = setValueStudent();
        boolean flag = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == searchId) {
                students.set(i, new Student(searchId,
                        strings[0], strings[1], strings[2],
                        strings[3], strings[4], strings[5]));
                flag = true;
                break;
            }
        }
        if (flag) {
            var dom = new DomParse(filePath);
            dom.setDomNodes(students);
        } else {
            System.out.println("Такого ученика нет!");
        }

    }

    private static void deleteStudent(String filePath) {
        var sax = new SAXParse();
        var students = sax.readerSaxDocument(filePath);
        System.out.println("Введите id ученика:");
        var searchId = getNum();
        boolean flag = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == searchId) {
                students.remove(i);
                flag = true;
                break;
            }
        }
        if (flag) {
            var dom = new DomParse(filePath);
            dom.setDomNodes(students);
        } else {
            System.out.println("Такого студента нет!");
        }

    }

    public static int getNum() {
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println("Введенно некоректное значение!");
            System.out.print("Введите значение повторно: ");
            sc.next();
        }
        return sc.nextInt();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
