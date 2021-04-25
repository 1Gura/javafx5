package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TextArea textArea;

    @FXML
    private Button XMLAll;

    @FXML
    private Button BDAll;

    @FXML
    private Button parseXMLtoBD;

    @FXML
    private Button parseBDtoXML;

    @FXML
    private Button btnId;

    @FXML
    private Button btnIdDb;

    @FXML
    private TextField inputId;

    @FXML
    private TextField inputIdDb;

    @FXML
    private Button addXML;

    @FXML
    private Button changeXML;

    @FXML
    private Button deleteXML;

    @FXML
    private Button addBD;

    @FXML
    private Button changeBD;

    @FXML
    private Button deleteBD;

    @FXML
    private TextField changeInputXML;

    @FXML
    private TextField changeInputDB;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private TextField inputDeleteXML;

    @FXML
    private TextField inputDeleteDB;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    private String filePath;

    private HBox addStudentXML() {
        var label1 = new Label("Введите имя:");
        var label2 = new Label("Введите фамилию:");
        var label3 = new Label("Введите отчество:");
        var label4 = new Label("Введите школу:");
        var label5 = new Label("Введите класс:");
        var label6 = new Label("Введите возраст:");
        var label7 = new Label("");
        var textField1 = new TextField();
        var textField2 = new TextField();
        var textField3 = new TextField();
        var textField4 = new TextField();
        var textField5 = new TextField();
        var textField6 = new TextField();
        var button = new Button("Добавить");
        button.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var students = sax.readerSaxDocument(filePath);
            var newStudent = new Student(
                    students.size() + 1,
                    textField1.getText(),
                    textField2.getText(),
                    textField3.getText(),
                    textField4.getText(),
                    textField5.getText(),
                    textField6.getText()
            );
            if (!textField1.getText().equals("") &&
                    !textField2.getText().equals("") &&
                    !textField3.getText().equals("") &&
                    !textField4.getText().equals("") &&
                    !textField5.getText().equals("") &&
                    !textField6.getText().equals("") && textField6.getText().trim().matches("^\\d+$")) {
                students.add(newStudent);
                var dom = new DomParse(filePath);
                dom.setDomNodes(students);
                label7.setText("Ученик добавлен!");
            } else {
                label7.setText("Ученик не добавлен!");

            }
        });
        HBox hBox = new HBox(new FlowPane(Orientation.VERTICAL, 20, 20, label1, textField1, label2, textField2, label3, textField3, label4, textField4, label5, textField5, label6, textField6, button, label7));
        hBox.setMinWidth(400);
        hBox.setMinHeight(600);
        return hBox;
    }

    private HBox addStudentBD() {
        var label1 = new Label("Введите имя:");
        var label2 = new Label("Введите фамилию:");
        var label3 = new Label("Введите отчество:");
        var label4 = new Label("Введите школу:");
        var label5 = new Label("Введите класс:");
        var label6 = new Label("Введите возраст:");
        var label7 = new Label("");
        var textField1 = new TextField();
        var textField2 = new TextField();
        var textField3 = new TextField();
        var textField4 = new TextField();
        var textField5 = new TextField();
        var textField6 = new TextField();
        var button = new Button("Добавить");
        button.setOnAction(actionEvent -> {
            var mySql = new MySqlParse();
            var newStudent = new Student(
                    0,
                    textField1.getText(),
                    textField2.getText(),
                    textField3.getText(),
                    textField4.getText(),
                    textField5.getText(),
                    textField6.getText()
            );
            if (!textField1.getText().equals("") &&
                    !textField2.getText().equals("") &&
                    !textField3.getText().equals("") &&
                    !textField4.getText().equals("") &&
                    !textField5.getText().equals("") &&
                    !textField6.getText().equals("") && textField6.getText().trim().matches("^\\d+$")) {
                try {
                    mySql.addNewRecord(newStudent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                var dom = new DomParse(filePath);
                label7.setText("Ученик добавлен!");
            } else {
                label7.setText("Ученик не добавлен!");

            }
        });
        HBox hBox = new HBox(new FlowPane(Orientation.VERTICAL, 20, 20, label1, textField1, label2, textField2, label3, textField3, label4, textField4, label5, textField5, label6, textField6, button, label7));
        hBox.setMinWidth(400);
        hBox.setMinHeight(600);
        return hBox;
    }

    private HBox changeStudentBD(int id) {
        Student student = null;
        var mySql = new MySqlParse();
        try {
            var result  = mySql.searchRecord(id);
            while (result.next()) {
                student = new Student(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("surname"),
                        result.getString("patronymic"),
                        result.getString("school"),
                        result.getString("clas"),
                        result.getString("age")
                );
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        var label0 = new Label("Id:" + id);
        var label1 = new Label("Введите имя:");
        var label2 = new Label("Введите фамилию:");
        var label3 = new Label("Введите отчество:");
        var label4 = new Label("Введите школу:");
        var label5 = new Label("Введите класс:");
        var label6 = new Label("Введите возраст:");
        var label7 = new Label("");
        var textField1 = new TextField(student.getName());
        var textField2 = new TextField(student.getSurname());
        var textField3 = new TextField(student.getPatronymic());
        var textField4 = new TextField(student.getSchool());
        var textField5 = new TextField(student.getClas());
        var textField6 = new TextField(student.getAge());
        var button = new Button("Изменить");
        button.setOnAction(actionEvent -> {
            var newStudent = new Student(
                    0,
                    textField1.getText(),
                    textField2.getText(),
                    textField3.getText(),
                    textField4.getText(),
                    textField5.getText(),
                    textField6.getText()
            );
            if (!textField1.getText().equals("") &&
                    !textField2.getText().equals("") &&
                    !textField3.getText().equals("") &&
                    !textField4.getText().equals("") &&
                    !textField5.getText().equals("") &&
                    !textField6.getText().equals("") && textField6.getText().trim().matches("^\\d+$")) {
                try {
                    mySql.updateRecord(id, newStudent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                label7.setText("Ученик добавлен!");
            } else {
                label7.setText("Ученик не добавлен!");

            }
        });
        HBox hBox = new HBox(new FlowPane(Orientation.VERTICAL, 20, 20, label0, label1, textField1, label2, textField2, label3, textField3, label4, textField4, label5, textField5, label6, textField6, button, label7));
        hBox.setMinWidth(400);
        hBox.setMinHeight(600);
        return hBox;
    }


    private HBox changeStudent(Student student, int index) {
        var label0 = new Label("Id:" + student.getId());
        var label1 = new Label("Введите имя:");
        var label2 = new Label("Введите фамилию:");
        var label3 = new Label("Введите отчество:");
        var label4 = new Label("Введите школу:");
        var label5 = new Label("Введите класс:");
        var label6 = new Label("Введите возраст:");
        var label7 = new Label("");
        var textField1 = new TextField(student.getName());
        var textField2 = new TextField(student.getSurname());
        var textField3 = new TextField(student.getPatronymic());
        var textField4 = new TextField(student.getSchool());
        var textField5 = new TextField(student.getClas());
        var textField6 = new TextField(student.getAge());
        var button = new Button("Изменить");
        button.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var students = sax.readerSaxDocument(this.filePath);
            var id = student.getId();
            students.set(index, new Student(
                    id,
                    textField1.getText(),
                    textField2.getText(),
                    textField3.getText(),
                    textField4.getText(),
                    textField5.getText(),
                    textField6.getText()
            ));
            var dom = new DomParse(filePath);
            dom.setDomNodes(students);
        });
        HBox hBox = new HBox(new FlowPane(Orientation.VERTICAL, 20, 20, label0, label1, textField1, label2, textField2, label3, textField3, label4, textField4, label5, textField5, label6, textField6, button, label7));
        hBox.setMinWidth(400);
        hBox.setMinHeight(600);
        return hBox;
    }

    private void newWindow(HBox hBox) {
        hBox.setAlignment(Pos.CENTER);
        Stage stage2 = new Stage();
        Scene scene = new Scene(hBox, 300, 600);
        stage2.setScene(scene);
        stage2.setTitle("Ученик");
        stage2.initModality(Modality.NONE);
        stage2.show();
    }

    @FXML
    void initialize() {
        var prop = new PropertiesParse();
        var catalog = prop.readCatalogRoot();
        this.filePath = catalog + "\\file.xml";
        XMLAll.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var students = sax.readerSaxDocument(filePath);
            textArea.setText("");
            if (students.size() > 0) {
                for (Student student : students) {
                    textArea.setText(textArea.getText() + student.toString() + "\n");
                }
            }
        });
        BDAll.setOnAction(actionEvent -> {
            try {
                var mySqlObj = new MySqlParse();
                var result = mySqlObj.getAll();
                textArea.setText("");
                try {
                    while (result.next()) {
                        Student student = new Student(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("surname"),
                                result.getString("patronymic"),
                                result.getString("school"),
                                result.getString("clas"),
                                result.getString("age")
                        );
                        textArea.setText(textArea.getText() + student.toString() + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        parseXMLtoBD.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var parsing = new Parsing(sax.readerSaxDocument(filePath));
            parsing.parseXMLtoDB();
        });

        parseBDtoXML.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var dom = new DomParse(filePath);
            var parsing = new Parsing(sax.readerSaxDocument(filePath), dom);
            parsing.parseDBtoXML();
        });

        btnId.setOnAction(actionEvent -> {
            var id = inputId.getText();
            if (!id.equalsIgnoreCase("0") && id.length() > 0 && !id.equalsIgnoreCase("null")) {
                var sax = new SAXParse();
                var student = sax.searchSaxDocument(filePath, id);
                textArea.setText(student != null ? student.toString() : "Такого студента нет!");
            }
        });

        btnIdDb.setOnAction(actionEvent -> {
            var id = inputIdDb.getText();
            if (!id.equalsIgnoreCase("0") && id.length() > 0 && !id.equalsIgnoreCase("null")) {
                try {
                    var mySqlObj = new MySqlParse();
                    var result = mySqlObj.searchRecord(Integer.parseInt(id));
                    while (result.next()) {
                        Student student = new Student(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("surname"),
                                result.getString("patronymic"),
                                result.getString("school"),
                                result.getString("clas"),
                                result.getString("age")
                        );
                        textArea.setText(student != null ? student.toString() : "Такого ученика нет!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addXML.setOnAction(actionEvent -> {
            HBox hBox = addStudentXML();
            newWindow(hBox);
        });
        changeXML.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var students = sax.readerSaxDocument(filePath);
            boolean flag = false;
            Student searchStudent = null;
            int searchId = -1;
            int index = -1;
            for (int i = 0; i < students.size(); i++) {
                try {
                    searchId = Integer.parseInt(changeInputXML.getText());
                } catch (Exception e) {
                    label1.setText("Можно ввести только числа!");
                }
                if (students.get(i).getId() == searchId) {
                    index = i;
                    searchStudent = students.get(i);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                HBox hBox = changeStudent(searchStudent, index);
                newWindow(hBox);
                label1.setText("");
            } else {
                label1.setText("Такого ученика нет!");
            }
        });

        deleteXML.setOnAction(actionEvent -> {
            var sax = new SAXParse();
            var students = sax.readerSaxDocument(filePath);
            boolean flag = false;
            int id = -1;
            for (int i = 0; i < students.size(); i++) {
                try {
                    id = Integer.parseInt(inputDeleteXML.getText());
                } catch (Exception e) {
                    label3.setText("Можно ввести только цифры!");
                }
                if (students.get(i).getId() == id) {
                    students.remove(i);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                var dom = new DomParse(filePath);
                dom.setDomNodes(students);
                label3.setText("");
            } else {
                label3.setText("Такого студента нет!");
            }
        });
        addBD.setOnAction(actionEvent -> {
            HBox hBox = addStudentBD();
            newWindow(hBox);
        });
        changeBD.setOnAction(actionEvent -> {
            HBox hBox = changeStudentBD(Integer.parseInt(changeInputDB.getText()));
            newWindow(hBox);
        });
        deleteBD.setOnAction(actionEvent -> {
                var mySql = new MySqlParse();
            try {
                mySql.deleteRecord(Integer.parseInt(inputDeleteDB.getText()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            label4.setText("Ученик удален!");
        });
    }
}
