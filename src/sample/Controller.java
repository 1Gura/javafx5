package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private BottomNavigationButton addXML;

    @FXML
    private BottomNavigationButton changeXML;

    @FXML
    private BottomNavigationButton deleteXML;

    @FXML
    private BottomNavigationButton addBD;

    @FXML
    private BottomNavigationButton changeBD;

    @FXML
    private BottomNavigationButton deleteBD;

    @FXML
    void initialize() {
        var prop = new PropertiesParse();
        var catalog = prop.readCatalogRoot();
        String filePath = catalog + "\\file.xml";
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
                        textArea.setText(student != null ? student.toString(): "Такого ученика нет!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
