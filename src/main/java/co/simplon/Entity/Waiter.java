package co.simplon.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Waiter {
    private int WaiterID;
    private String firstname;
    private String Lastname;

    public Waiter(int waiterID, String firstname, String lastname) {
        WaiterID = waiterID;
        this.firstname = firstname;
        Lastname = lastname;
    }

    public Waiter(String firstname, String lastname) {
        this.firstname = firstname;
        this.Lastname = lastname;
    }

    @Override
    public String toString() {
        return "Waiter " + WaiterID + "  " + firstname + "  " + Lastname + '\'';
    }


    // gérer la sauvegarde de waiter dans la base
    public void saveWaiter(Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("INSERT INTO waiter (firstname, lastname)  VALUES ('" + firstname + "','" + Lastname + "')");
        ordreSQL.close();
    }

    // gerer l'affichage des serveurs
    public static List<Waiter> getwaiter(Connection connection) throws SQLException {
        // Pouvoir lister des waiter
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("SELECT * from waiter");

        List<Waiter> waiterList = new ArrayList<>();

        while (resultats.next()) {
            Waiter dbwaiter = new Waiter(resultats.getInt("WaiterID"),
                    resultats.getString("firstname"),
                    resultats.getString("Lastname"));
            waiterList.add(dbwaiter);

            System.out.println(dbwaiter);
        }

        resultats.close();
        ordreSQL.close();

        return waiterList;
    }


}
