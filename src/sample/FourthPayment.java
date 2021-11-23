package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbAccess.dbConnectionImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class FourthPayment implements Initializable {
    public TextField userNameF;
    public TextField nameF;
    public TextField Phone;
    public TextField seatF;
    public TextField totalAmount;
    public TextField payAmount;
    public TextField DateF;
    private dbConnectionImpl dbConnection;

    String name_f;
    String phone_f;
    String seat_f;
    String totalAmount_F;
    String date_f;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new dbConnectionImpl();
    }
    String userNames;

    // For Name
    void name(String userName) {
        userNames = userName;
        Connection connection = dbConnection.openConnection();
        String sql =String.format("select * from userInfo where userName = \"%s\"",
                userName);
        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet != null){
                while (resultSet.next()){
                    name_f = resultSet.getString("Name");
                    phone_f = resultSet.getString("Phone");
                }
                nameF.setText(name_f);
                Phone.setText(phone_f);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(connection != null){
                dbConnection.closeConnection(connection);
            }
        }
    }


    // For Amount,Phone, Date
    void dateSeatAmount(String userName) {
        Connection connection = dbConnection.openConnection();
        String sql =String.format("select * from seatBooking where userName = \"%s\"",
                userName);
        /*String sql2 = String.format("SELECT userInfo.Name, userInfo.Phone, seatBooking.Date, seatBooking.Seats," +
                "seatBooking.Amount\n" +
                "FROM userInfo, seatBooking \n" +
                "WHERE userInfo.username=seatBooking.username");*/
        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet != null){
                while (resultSet.next()){
                    date_f = resultSet.getString("Date");
                    seat_f = resultSet.getString("Seats");
                    totalAmount_F = resultSet.getString("Amount");
                }

                DateF.setText(date_f);
                seatF.setText(seat_f);
                totalAmount.setText(totalAmount_F);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(connection != null){
                dbConnection.closeConnection(connection);
            }
        }
    }

    public void payAmount(ActionEvent actionEvent) throws IOException {
        String pay = payAmount.getText();
        double totalAmount = Double.parseDouble(totalAmount_F);

        if(!pay.isEmpty()){
            double payF = Double.parseDouble(pay);
            if (payF==totalAmount){
                Connection connection = dbConnection.openConnection();
                String sql =String.format("UPDATE seatBooking\n" +
                                "SET Status = 'paid'\n" +
                                "where userName = \"%s\"",
                        userNames);
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
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
                alert.setTitle("Successful");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you want to take Ticket ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fifthTicket.fxml"));
                    Parent fifthPage = loader.load();

                    FifthTicket fifthTicket = loader.getController();
                    fifthTicket.ticket1(userNames);
                    fifthTicket.ticket2(userNames);

                    Scene fifthPageScene = new Scene(fifthPage, 600, 400);
                    Stage fifthPageStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    fifthPageStage.setScene(fifthPageScene);
                    fifthPageStage.show();

                } else {
                    Parent first = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
                    Scene firstScene = new Scene(first, 600, 400);
                    Stage firstStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    firstStage.setScene(firstScene);
                    firstStage.show();
                }

            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid Amount !!!");
                alert.showAndWait();

                payAmount.clear();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Enter Amount !!!");
            alert.showAndWait();
        }

    }

    public void notNow(ActionEvent actionEvent) throws IOException {

        Parent firstPage = FXMLLoader.load(getClass().getResource("checkUserPayment.fxml"));
        Scene firstScene = new Scene(firstPage, 600,400);
        Stage firstStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        firstStage.setScene(firstScene);
        firstStage.show();
    }
}
