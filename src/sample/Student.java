package sample;

public class Student {
    protected  int id;
    protected String name;
    protected String surname;
    protected String patronymic;
    protected String school;
    protected String clas;
    protected String age;

    public Student(int id, String name, String surname, String patronymic, String school, String clas, String age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.school = school;
        this.clas = clas;
        this.age = age;
    }
    @Override
    public String toString() {
        return "Id:"+this.id + "\n"
                +"Имя: " + this.name + "\n"
                +"Фамилия: " + this.surname + "\n"
                +"Отчество: " + this.patronymic + "\n"
                +"Школа: " + this.school + "\n"
                +"Класс: " + this.clas + "\n"
                +"Возраст: " + this.age + "\n";
    }

    public String getStudent() {
        return "Id:"+this.id + "\n"
                +"Имя: " + this.name + "\n"
                +"Фамилия: " + this.surname + "\n"
                +"Отчество: " + this.patronymic + "\n"
                +"Школа: " + this.school + "\n"
                +"Класс: " + this.clas + "\n"
                +"Возраст: " + this.age + "\n";
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }
    public String getPatronymic() {
        return this.patronymic;
    }
    public String getSchool() {
        return this.school;
    }
    public String getClas() {
        return this.clas;
    }
    public String getAge() {
        return this.age;
    }
}
