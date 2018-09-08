package sample.controllers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Alert.DialogManager;
import sample.objects.User;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditDialogController implements Initializable {
    @FXML
    private TextField textField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField birthdayField;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    private User user;
    private ResourceBundle resourceBundle;
    private OnActionListener onActionListener;

    public void setUser(User user) {
        if (user == null) {
            return;
        }
        this.user = user;
        textField.setText(user.getName());
        ageField.setText(user.getAge());
        birthdayField.setText(user.getBirthday());

    }

    public User getUser() {
        return user;
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public void actionClose(ActionEvent actionEvent) {
        if (onActionListener != null) {
            onActionListener.onCloseAction();
        }
        hideStage(actionEvent);
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        user.setName(textField.getText());
        user.setAge(ageField.getText());
        user.setBirthday(birthdayField.getText());
        if (onActionListener != null) {
            onActionListener.onSaveAction(user);
        }
        hideStage(actionEvent);
    }

    private void hideStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private boolean checkValues() {
        if (textField.getText().trim().length() == 0 || ageField.getText().trim().length() == 0 || birthdayField.getText().trim().length() == 0) {
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("ЗАполните"));
            return false;
        }
        return true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }

    public interface OnActionListener {
        void onSaveAction(User user);
        void onCloseAction();
    }
}
