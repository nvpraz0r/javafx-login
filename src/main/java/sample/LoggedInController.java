package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoggedInController implements Initializable{
    @FXML
    private Button button_logout;

    @FXML
    private Label label_welcome;

    @FXML
    private Label label_super_pw;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml", "Log In", null, null);
            }
        });
    }

    /**
     * This function displays the user's information once they log in
     * @param username the user's display name
     * @param super_pw the "super password" radio button that is selected when an account in created
     */
    public void setUserInformation(String username, String super_pw){
        label_welcome.setText("Welcome " + username + "!");
        label_super_pw.setText("Your super secret password is: " + super_pw + ".");
    }
}
