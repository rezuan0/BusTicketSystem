package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbAccess.dbConnectionImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FifthTicket implements Initializable {
    public TextField nameField;
    public TextArea addressField;
    public TextField phoneField;
    public TextField seatsField;
    public TextField usernameF;
    public TextField dateF;

    private dbConnectionImpl dbConnection;

    String nameD;
    String addressD;
    String phoneD;
    String seatsD;
    String dateD;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new dbConnectionImpl();
    }

    public void search(ActionEvent actionEvent) {
        String uName = usernameF.getText();
        if(!uName.isEmpty()){
            ticket1(uName);
            ticket2(uName);
        }
    }

    void ticket1(String uName) {

        Connection connection = dbConnection.openConnection();
        String sql =String.format("select * from userInfo where userName = \"%s\"",
                uName);
        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet != null){
                while (resultSet.next()){

                    nameD = resultSet.getString("Name");
                    addressD = resultSet.getString("Address");
                    phoneD = resultSet.getString("phone");

                }
                nameField.setText(nameD);
                addressField.setText(addressD);
                phoneField.setText(phoneD);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(connection != null){
                dbConnection.closeConnection(connection);
            }
        }
    }

    void ticket2(String uName) {
        Connection connection = dbConnection.openConnection();
        String sql =String.format("select * from seatBooking where userName = \"%s\"",
                uName);

        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet != null){
                while (resultSet.next()){
                    seatsD = resultSet.getString("seats");
                    dateD = resultSet.getString("Date");
                }
                seatsField.setText(seatsD);
                dateF.setText(dateD);
                phoneField.setText(phoneD);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(connection != null){
                dbConnection.closeConnection(connection);
            }
        }

    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        Parent firstPage = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
        Scene firstScene = new Scene(firstPage, 600,400);
        Stage firstStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        firstStage.setScene(firstScene);
        firstStage.show();
    }

}
