package sample;

import com.mysql.cj.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbAccess.dbConnectionImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ThirdSeatBooking implements Initializable {
    public TextField Name;
    public TextField Phone;
    public CheckBox A1F,A2F;
    public CheckBox B1F,B2F,B3F;
    public CheckBox C1F,C2F,C3F;
    public CheckBox D1F,D2F,D3F;
    public CheckBox E1F,E2F,E3F;
    public CheckBox F1F,F2F,F3F;
    public CheckBox G1F,G2F,G3F;
    public CheckBox H1F,H2F,H3F,H4F;
    public int c = 0, i = 0;
    public String[] arr = new String[24];
    public DatePicker dateF;


    String NameD;
    String PhoneD;
    private dbConnectionImpl dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dbConnection = new dbConnectionImpl();

    }

    public void seatBooked(ActionEvent actionEvent) throws IOException {
        LocalDate date = dateF.getValue();

        if(A1F.isSelected()){
            c++;
            String a1 = A1F.getText();
            arr[i] = a1;
            i++;
        }
        if(A2F.isSelected()){
            c++;
            String a2 = A2F.getText();
            arr[i] = a2;
            i++;
        }
        if(B1F.isSelected()){
            c++;
            String b1 = B1F.getText();
            arr[i] = b1;
            i++;
        }
        if(B2F.isSelected()){
            c++;
            String b2 = B2F.getText();
            arr[i] = b2;
            i++;
        }
        if(B3F.isSelected()){
            c++;
            String b3 = B3F.getText();
            arr[i] = b3;
            i++;
        }
        if(C1F.isSelected()){
            c++;
            String c1 = C1F.getText();
            arr[i] = c1;
            i++;
        }
        if(C2F.isSelected()){
            c++;
            String c2 = C2F.getText();
            arr[i] = c2;
            i++;
        }
        if(C3F.isSelected()){
            c++;
            String c3 = C3F.getText();
            arr[i] = c3;
            i++;
        }
        if(D1F.isSelected()){
            c++;
            String d1 = D1F.getText();
            arr[i] = d1;
            i++;
        }
        if(D2F.isSelected()){
            c++;
            String d2 = D2F.getText();
            arr[i] = d2;
            i++;
        }
        if(D3F.isSelected()){
            c++;
            String d3 = D3F.getText();
            arr[i] = d3;
            i++;
        }
        if(E1F.isSelected()){
            c++;
            String e1 = E1F.getText();
            arr[i] = e1;
            i++;
        }
        if(E2F.isSelected()){
            c++;
            String e2 = E2F.getText();
            arr[i] = e2;
            i++;
        }
        if(E3F.isSelected()){
            c++;
            String e3 = E3F.getText();
            arr[i] = e3;
            i++;
        }
        if(F1F.isSelected()){
            c++;
            String f1 = F1F.getText();
            arr[i] = f1;
            i++;
        }
        if(F2F.isSelected()){
            c++;
            String f2 = F2F.getText();
            arr[i] = f2;
            i++;
        }
        if(F3F.isSelected()){
            c++;
            String f3 = F3F.getText();
            arr[i] = f3;
            i++;
            //System.out.printf(F3F.getText()+"\n");
        }
        if(G1F.isSelected()){
            c++;
            String g1 = G1F.getText();
            arr[i] = g1;
            i++;
        }
        if(G2F.isSelected()){
            c++;
            String g2 = G2F.getText();
            arr[i] = g2;
            i++;
        }
        if(G3F.isSelected()){
            c++;
            String g3 = G3F.getText();
            arr[i] = g3;
            i++;
        }
        if(H1F.isSelected()){
            c++;
            String h1 = H1F.getText();
            arr[i] = h1;
            i++;
        }
        if(H2F.isSelected()){
            c++;
            String h2 = H2F.getText();
            arr[i] = h2;
            i++;
        }
        if(H3F.isSelected()){
            c++;
            String h3 = H3F.getText();
            arr[i] = h3;
            i++;
        }
        if(H4F.isSelected()){
            c++;
            String h4 = H4F.getText();
            arr[i] = h4;
            i++;
        }

        if(i!=0){
            String seat = "";
            for(int j = 0; j<arr.length; j++){
                if(arr[j]!=null){
                    //System.out.println(arr[j]);
                    seat +=  arr[j]+",";
                }
            }
            System.out.println(seat);
            String result = null;
            if ((seat != null) && (seat.length() > 0)) {
                result = seat.substring(0, seat.length() - 1);
            }
            System.out.println(result);
            String amount = String.valueOf(c * 1100);
            reserveSeat(userNames,date,result,amount);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fourthPayment.fxml"));
            Parent fourthPage = loader.load();

            FourthPayment fourthPayment = loader.getController();
            fourthPayment.dateSeatAmount(userNames);
            fourthPayment.name(userNames);

            Scene fourthPageScene = new Scene(fourthPage, 600, 400);
            Stage fourthPageStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            fourthPageStage.setScene(fourthPageScene);
            fourthPageStage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seat Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select seats ...");
            alert.showAndWait();
        }


    }

    private void reserveSeat(String userName, LocalDate date, String seat, String amount)  {

        Connection connection = dbConnection.openConnection();

        String sql = "INSERT INTO seatBooking(username, Date, Seats, Amount, Status) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, String.valueOf(date));
            statement.setString(3, seat);
            statement.setString(4, amount);
            statement.setString(5,"unpaid");

            int result = statement.executeUpdate();
            if(result>0){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Seats are Reserved for you..");
                alert.setContentText("Are you want to pay ?");


            }else{
                System.err.println("Failed to Insert");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(connection != null){
                dbConnection.closeConnection(connection);
            }
        }

    }

    public String userNames;


    // For Name & Phone
    public void namePhone(String userName) {
        userNames = userName;
        Connection connection = dbConnection.openConnection();

        String sql =String.format("select * from userInfo where userName = \"%s\"",
                userName);

        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet != null){
                while (resultSet.next()){

                    NameD = resultSet.getString("Name");
                    PhoneD = resultSet.getString("phone");
                }
                Phone.setText(PhoneD);
                Name.setText(NameD);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please Select seats & Date ...");
        alert.showAndWait();
    }

}
