package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbAccess.dbConnectionImpl;

import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FirstPage implements Initializable {
    public PasswordField passwordField;
    public TextField userNameField;
    public String passWord;
    public String username;

    private dbConnectionImpl dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new dbConnectionImpl();
    }






    public void logIn(javafx.event.ActionEvent actionEvent) throws IOException {

        String uName = userNameField.getText();
        String pass = passwordField.getText();

        if (!uName.isEmpty() && !pass.isEmpty()) {
            Connection connection = dbConnection.openConnection();

            String sql =String.format("select * from userInfo where userName = \"%s\"",
                    uName);
            try{

                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();

                if(resultSet != null){
                    while (resultSet.next()){

                        username = resultSet.getString("userName");
                        passWord = resultSet.getString("password");
                    }
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                if(connection != null){
                    dbConnection.closeConnection(connection);
                }
            }

            if(passWord.equals(pass)){

                System.out.println("LogIn");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("checkUserPayment.fxml"));
                Parent checkUser = loader.load();

                /*ThirdSeatBooking thirdSeatBooking = loader.getController();
                thirdSeatBooking.namePhone(username);*/
                CheckUserPayment checkUserPayment = loader.getController();
                checkUserPayment.getUsername(username);

                Scene checkUserScene = new Scene(checkUser, 600,400);
                Stage checkUserStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                checkUserStage.setScene(checkUserScene);
                checkUserStage.show();

            }else {
                System.err.println("Wrong Username & Password !!!");
                userNameField.clear();
                passwordField.clear();
            }

        } else {
            System.err.println("Fill up properly !!!");

        }


    }

    public void newAccount(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent newAccount = FXMLLoader.load(getClass().getResource("secondNewAccount.fxml"));
        Scene newAccountScene = new Scene(newAccount, 600, 400);
        Stage newAccountStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        newAccountStage.setScene(newAccountScene);
        newAccountStage.show();

    }
}
