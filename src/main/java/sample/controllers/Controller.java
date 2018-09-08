package sample.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Alert.DialogManager;
import sample.interfaces.Implementaciya.CollectionTree;
import sample.objects.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable, EditDialogController.OnActionListener {

    private CollectionTree treeimpl = new CollectionTree();

    private Stage mainStage;

    @FXML
    private Button addButton;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private TableView treeTableView;
    @FXML
    private TableColumn<User, String> columnName;

    @FXML
    private TableColumn<User, String> columnAge;

    @FXML
    private TableColumn<User, String> columnBirthday;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;
    private Stage editDialogStage;
    private ResourceBundle resourceBundle;
    private List<User> list;
    private Gson gson;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resource) {
        this.resourceBundle = resource;
        columnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<User, String>("age"));
        columnBirthday.setCellValueFactory(new PropertyValueFactory<User, String>("birthday"));
        gson = new GsonBuilder().create();
        fillData();
        initLoader();

    }

    private void fillData() {
        //treeimpl.fillTestData();
        String jsonString = readFromFile();
        User[] userArr = gson.fromJson(jsonString, User[].class);
        for (User user : userArr) {
            treeimpl.add(user);
        }
        list = FXCollections.observableArrayList();
        list.addAll(treeimpl.getUserList());
        treeTableView.setItems(treeimpl.getUserList());

    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("/edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();
            editDialogController.setOnActionListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionButtonPressed(javafx.event.ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }

        User selectedUser = (User) treeTableView.getSelectionModel().getSelectedItem();
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "addButton":
                editDialogController.setUser(new User());
                showDialog();
                treeimpl.add(editDialogController.getUser());
                break;


            case "buttonEdit":
                if (!userIsSelected(selectedUser)) {
                    return;
                }
                editDialogController.setUser(selectedUser);
                showDialog();
                break;

            case "buttonDelete":
                if (!userIsSelected(selectedUser)) {
                    return;
                }
                treeimpl.delete(selectedUser);
                break;
        }
    }

    @Override
    public void onSaveAction(User user) {
        User[] userArr = new User[0];
        String jsonString = gson.toJson(treeimpl.getUserList().toArray(userArr));
        writeToFile(jsonString);
    }

    @Override
    public void onCloseAction() {
        // do nothing
    }

    private boolean userIsSelected(User selectedUser) {
        if (selectedUser == null) {
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("ssd"));
            return false;
        }
        return true;
    }

    private void showDialog() {

        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);

        }
        editDialogStage.showAndWait();
    }

    private String readFromFile() {
        StringBuilder jsonString = new StringBuilder();
        try (FileReader reader = new FileReader("data.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                jsonString.append(c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return jsonString.toString();
    }

    private void writeToFile(String jsonString) {
        try (FileWriter writer = new FileWriter("data.txt", false)) {
            writer.write(jsonString);
            writer.flush();
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
}


