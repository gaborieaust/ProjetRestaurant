package co.simplon.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Plat {
    int PlatID;
    String Name;
    double pxunit;

    public Plat(int platID, String name, double pxunit) {
        PlatID = platID;
        Name = name;
        this.pxunit = pxunit;
    }

    @Override
    public String toString() {
        return "Plat " + PlatID + "  " + Name + "  " + pxunit + '\'';
    }

    // gérer l'affichage des plats
    public static List<Plat> getplat(Connection connection) throws SQLException {
        // Pouvoir lister des plats
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select * from plat");
        List<Plat> PlatList = new ArrayList<>();

        // execution de la boucle while pour l'affichage des plats
        while (resultats.next()) {
            Plat dbplat = new Plat(resultats.getInt("PlatID"),
                    resultats.getString("Name"),
                    resultats.getDouble("pxunit"));
            PlatList.add(dbplat);

            System.out.println(dbplat);
        }

        resultats.close();
        ordreSQL.close();

        return PlatList;

    }


}
