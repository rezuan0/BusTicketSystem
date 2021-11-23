package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.dbAccess.dbConnectionImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CheckUserPayment implements Initializable {
    public String uName;
    private dbConnectionImpl dbConnection;

    public void getUsername(String userName) {
        uName = userName;
    }

    public void paymentWindow(javafx.event.ActionEvent actionEvent) throws IOException {
        if (checkSeats().equals("booked")) {
            if (checkPaymentStatus().equals("unpaid")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fourthPayment.fxml"));
                Parent fourthPage = loader.load();

                FourthPayment fourthPayment = loader.getController();
                fourthPayment.dateSeatAmount(uName);
                fourthPayment.name(uName);

                Scene fourthPageScene = new Scene(fourthPage, 600, 400);
                Stage fourthPageStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                fourthPageStage.setScene(fourthPageScene);
                fourthPageStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You already paid...");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete the booking first...");
            alert.showAndWait();
        }


    }

    public void printTicket(javafx.event.ActionEvent actionEvent) throws IOException {

        if (checkPaymentStatus().equals("paid")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fifthTicket.fxml"));
            Parent fifthPage = loader.load();

            FifthTicket fifthTicket = loader.getController();
            fifthTicket.ticket1(uName);
            fifthTicket.ticket2(uName);

            Scene fifthPageScene = new Scene(fifthPage, 600, 400);
            Stage fifthPageStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            fifthPageStage.setScene(fifthPageScene);
            fifthPageStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("please complete payment...");
            alert.showAndWait();
        }


    }


    public void seatBooking(javafx.event.ActionEvent actionEvent) throws IOException {

        if (checkSeats().equals("notBooked")) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("thirdSeatBooking.fxml"));
            Parent seatBooking = loader.load();

            ThirdSeatBooking thirdSeatBooking = loader.getController();
            thirdSeatBooking.namePhone(uName);

            Scene seatBookingScene = new Scene(seatBooking, 600, 400);
            Stage seatBookingStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            seatBookingStage.setScene(seatBookingScene);
            seatBookingStage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("you already booked please complete payment...");
            alert.showAndWait();

        }
    }


    public void deleteBooking(javafx.event.ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Not update yet...");
        alert.showAndWait();

    }

    public void logOut(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent newAccount = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
        Scene newAccountScene = new Scene(newAccount, 600, 400);
        Stage newAccountStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newAccountStage.setScene(newAccountScene);
        newAccountStage.show();

    }

    private String checkPaymentStatus() {
        String status = null;
        Connection connection = dbConnection.openConnection();

        String sql = String.format("select Status from seatBooking where userName = \"%s\"",
                uName);

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    status = resultSet.getString("status");
                }

                if (status.equals("paid")) {
                    return "paid";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dbConnection.closeConnection(connection);
        }

        return "unpaid";
    }

    private String checkSeats() {
        String seats = null;
        Connection connection = dbConnection.openConnection();

        String sql = String.format("select Seats from seatBooking where userName = \"%s\"",
                uName);

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    seats = resultSet.getString("Seats");
                    System.out.println(seats);
                }

                System.out.println(seats);
                System.out.println(seats);

                if (seats == null) {
                    return "notBooked";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dbConnection.closeConnection(connection);
        }
        return "booked";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new dbConnectionImpl();
    }
}
