package sample.objects;

import javafx.beans.property.SimpleStringProperty;


//Simple(String,Integer  и тд,)Property автообновляет
public class User {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty age = new SimpleStringProperty("");
    private SimpleStringProperty birthday = new SimpleStringProperty("");

    public User() {

    }


    public User(String name, String age, String birthday) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.birthday = new SimpleStringProperty(birthday);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
