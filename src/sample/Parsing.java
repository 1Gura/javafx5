package sample;

import java.util.ArrayList;

public class Parsing {
    protected ArrayList<Student> students = new ArrayList<Student>();
    protected DomParse domParse;

    public Parsing(ArrayList<Student> studentsXML) {
        this.students = studentsXML;
    }

    public Parsing(ArrayList<Student> studentsXML, DomParse domParse) {
        this.students = studentsXML;
        this.domParse = domParse;
    }

    public void parseXMLtoDB() {
        var mySqlObj = new MySqlParse();
        for (Student student : this.students) {
            try {
                mySqlObj.addNewRecord(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void parseDBtoXML() {
        var mySqlObj = new MySqlParse();
        try {
            var result = mySqlObj.getAll();
            while (result.next()) {
                this.students.add(new Student(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("surname"),
                        result.getString("patronymic"),
                        result.getString("school"),
                        result.getString("clas"),
                        result.getString("age")
                ));
            }
            this.domParse.setDomNodes(this.students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
