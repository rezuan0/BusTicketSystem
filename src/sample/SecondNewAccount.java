package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.dbAccess.dbConnectionImpl;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class SecondNewAccount implements Initializable {
    public TextField firstName;
    public TextField lastName;
    public TextArea addressField;
    public TextField email;
    public TextField Phone;

    public ComboBox<String> comboBox;
    public PasswordField passField;
    public PasswordField conPassField;
    public Random random = new Random();

    private dbConnectionImpl dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBox.getItems().add("Male");
        comboBox.getItems().add("Female");
        comboBox.getItems().add("Other");

        dbConnection = new dbConnectionImpl();

    }

    public void createAccount(javafx.event.ActionEvent actionEvent) throws IOException {
        String fName = firstName.getText();
        String lName = lastName.getText();
        String address = addressField.getText();
        String eMail = email.getText();
        String gender = comboBox.getSelectionModel().getSelectedItem();
        String phone = Phone.getText();
        String password = passField.getText();
        String conPassword = conPassField.getText();
        if(fName.equals("") || lName.equals("")|| address.equals("") || eMail.equals("")
                || gender.equals("") || phone.equals("")|| password.equals("")||conPassword.equals("")){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill up all the Field !!!");
            alert.showAndWait();

            passField.clear();
            conPassField.clear();
        }
        else{
            if (password.equals(conPassword)) {

                System.out.println(fName + "\t" + lName + "\t" + address + "\t" + eMail + "\t" +
                        gender + "\t" + phone);

                String fullName = fName+" "+lName;
                String  usr = String.valueOf(random.nextInt(1000));
                String username = lName + usr;

                Connection connection = dbConnection.openConnection();

                String sql = "INSERT INTO userInfo(userName, Name, Address, Email, Gender, Phone, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, fullName);
                    statement.setString(3, address);
                    statement.setString(4, eMail);
                    statement.setString(5, gender);
                    statement.setString(6, phone);
                    statement.setString(7, password);

                    int result = statement.executeUpdate();
                    if(result>0){
                        System.out.println("Data Insert Successfully");
                    }else{
                        System.out.println("Failed to Insert");
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }finally {
                    if(connection != null){
                        dbConnection.closeConnection(connection);
                    }
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(username);
                alert.setContentText("This is your user name. Don't forget it !!!");

                alert.showAndWait();

                Parent firstPage = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
                Scene firstScene = new Scene(firstPage, 600,400);
                Stage firstStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                firstStage.setScene(firstScene);
                firstStage.show();

            }
            else {
                System.out.println("Password Not Match !!!");
                passField.clear();
                conPassField.clear();
            }
        }
    }

    public void backPage(javafx.event.ActionEvent actionEvent) throws IOException  {
        Parent newAccount = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
        Scene newAccountScene = new Scene(newAccount, 600, 400);
        Stage newAccountStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newAccountStage.setScene(newAccountScene);
        newAccountStage.show();
    }


}
