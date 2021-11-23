package sample.dbAccess;

import java.sql.Connection;

public interface dbConnection {

    Connection openConnection();
    void closeConnection(Connection connection);

}
