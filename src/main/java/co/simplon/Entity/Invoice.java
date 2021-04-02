package co.simplon.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Invoice {
    int invoiceID;
    int Waiterfk;
    int Tablefk;

    // on met en place les  constructeurs

    public Invoice(int invoiceID, int waiterfk, int tablefk) {
        this.invoiceID = invoiceID;
        Waiterfk = waiterfk;
        Tablefk = tablefk;
    }

    public Invoice(int waiterfk, int tablefk) {
        Waiterfk = waiterfk;
        Tablefk = tablefk;
    }

    @Override
    public String toString() {
        return "Invoice{" + invoiceID + "  " + Waiterfk + "  " + Tablefk + '\'' + '}';
    }


    // gérer la sauvegarde des invoices dans la base de données
    public void saveInvoice(Connection connection) throws SQLException {
        int id = 0;
        Scanner scanner = new Scanner(System.in);
        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("INSERT INTO invoice (waiterfk, tablefk)  VALUES ('" + Waiterfk + "','" + Tablefk + "')", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ordreSQL.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        System.out.println("tu viens ici 1");
        Plat.getplat(connection);
        System.out.println("ou bien ici");
        System.out.println("quel est le plat (ID) ");
        int IDPlat = scanner.nextInt();
        System.out.println("quelle est la quantité ");
        int quantity = scanner.nextInt();
        // insérer les valeures saisies dans la table jointure ( sur base ID que l'on récupère à la génération de la facture
        try {
            ordreSQL.execute("INSERT INTO tablejointure (invoicefk,platfk,quantity) VALUES ('" + id + "','" + IDPlat + "', '" + quantity + "')");
        } catch (SQLException exception) {
            // Ma gestion du problème
            System.out.println("Erreur de connexion à la base de données");
            exception.printStackTrace();
        }
    }
}


