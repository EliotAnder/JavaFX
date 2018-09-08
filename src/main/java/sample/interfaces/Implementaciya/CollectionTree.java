package sample.interfaces.Implementaciya;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.Tree;
import sample.objects.User;

public class CollectionTree implements Tree {
    private ObservableList<User> userList = FXCollections.observableArrayList();


    @Override
    public void add(User user) {
        userList.add(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {
        userList.remove(user);
    }

    public ObservableList<User> getUserList() {
        return userList;
    }


    public void fillTestData() {
        userList.add(new User("Vanya", "20", "24/07/1997"));
        userList.add(new User("Lesha", "30", "14/01/1968"));
        userList.add(new User("Peta", "40", "11/03/2001"));
        userList.add(new User("Jora", "11", "21/04/2019"));

    }

}
