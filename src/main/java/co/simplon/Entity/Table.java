package co.simplon.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableID;
    private String Nom;
    private int NumberGuests;

    public Table(int tableID, String nom, int numberGuests) {
        this.tableID = tableID;
        Nom = nom;
        NumberGuests = numberGuests;
    }

    public Table(String nom, int numberGuests) {
        this.Nom = nom;
        this.NumberGuests = numberGuests;
    }

    @Override
    public String toString() {
        return "Table{" + tableID + "  " + Nom + "  " + NumberGuests + '\'' + '}';
    }


    //gerer le nouveau client dans la base
    public void saveTable(Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("INSERT INTO tables (\"Nom\", \"NumberGuests\") VALUES ('" + Nom + "','" + NumberGuests + "')");
        ordreSQL.close();
    }

    public static List<Table> getTable(Connection connection) throws SQLException {
        // Pouvoir lister des tables
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select * from tables");
        List<Table> TableList = new ArrayList<>();

        while (resultats.next()) {
            Table dbtable = new Table(resultats.getInt("TableID"),
                    resultats.getString("Nom"),
                    resultats.getInt("NumberGuests"));
            TableList.add(dbtable);

            System.out.println(dbtable);
        }

        resultats.close();
        ordreSQL.close();

        return TableList;

    }
}