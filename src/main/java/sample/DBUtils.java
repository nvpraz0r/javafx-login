package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * DBUtils class changes the scene for the application and does all the communication with the database
 *
 */
public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String super_pw){
        Parent root = null;

        if(username != null && super_pw != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, super_pw);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, String super_pw){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "kingslayer");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exists");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, super_pw VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, super_pw);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Welcome!", username, super_pw);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "kingslayer");
//            preparedStatement = connection.prepareStatement("")
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
