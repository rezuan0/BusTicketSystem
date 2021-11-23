package sample.dbAccess;

import sample.utils.commonConstant;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnectionImpl implements dbConnection {
    @Override
    public Connection openConnection() {
        return getConnection();
    }

    @Override
    public void closeConnection(Connection connection) {
        try{
            if( connection != null && !connection.isClosed()){
                connection.close();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private Connection getConnection(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://"+ commonConstant.ipAddress +":"+ commonConstant.port+"/"+ commonConstant.dbName,
                    commonConstant.userName,
                    commonConstant.password);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
