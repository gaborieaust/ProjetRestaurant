package co.simplon;

import java.sql.Connection;

import co.simplon.Entity.Invoice;
import co.simplon.Entity.Table;
import co.simplon.Entity.Waiter;

import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;

public class Restaurant {
    // Instaurer la création d'un nouveau client
    private static void Newclient(Scanner sc) {

        try {
            String url = "jdbc:postgresql://localhost:5432/restaurant";
            String user = "postgres";
            String password = "postgres";
            Scanner scanner = new Scanner(System.in);
            Connection connection = DriverManager.getConnection(url, user, password);
            //Table.getTable(connection);
            //demander le nom du client
            System.out.println("Donne moi le nom du client stp : ");
            String nom = scanner.nextLine();
            System.out.println("Donne moi le nombre de couverts stp : ");
            int numberguests = scanner.nextInt();
            Table newTable = new Table(nom, numberguests);
            newTable.saveTable(connection);

            Table.getTable(connection);
            // selectionner l'ID correspondant au client


        } catch (SQLException exception) {
            // Ma gestion du problème
            System.out.println("Erreur de connexion à la base de données");
            exception.printStackTrace();
        }
    }

    // instaurer un nouveau serveur
    private static void Newserveur(Scanner sc) {


        try {
            String url = "jdbc:postgresql://localhost:5432/restaurant";
            String user = "postgres";
            String password = "postgres";
            Scanner scanner = new Scanner(System.in);
            Connection connection = DriverManager.getConnection(url, user, password);
            // j'affiche tous mes waiters dans la table
            Waiter.getwaiter(connection);

            // Mettre en place la creation de nouveau serveur
            System.out.println("Coucou, entre un nouveau serveur");
            System.out.println("Donne moi le prenom du nouveau serveur stp : ");
            String prenom = scanner.nextLine();
            System.out.println("Donne moi le nom du nouveau server stp : ");
            String nom = scanner.nextLine();
            // save a new waiter in the database
            Waiter newWaiter = new Waiter(nom, prenom);
            newWaiter.saveWaiter(connection);

            Waiter.getwaiter(connection);

            connection.close();

        } catch (
                SQLException exception) {
            // Ma gestion du problème
            System.out.println("Erreur de connexion à la base de données");
            exception.printStackTrace();
        }
    }

    //generer une nouvelle facture
    private static void newInvoice(Scanner sc) {

        try {
            String url = "jdbc:postgresql://localhost:5432/restaurant";
            String user = "postgres";
            String password = "postgres";
            Scanner scanner = new Scanner(System.in);
            Connection connection = DriverManager.getConnection(url, user, password);
            Waiter.getwaiter(connection);
            System.out.println("qui a servi la table : (il faut saisir l'ID du serveur) ");
            int IDServeur = scanner.nextInt();
            Table.getTable(connection);
            System.out.println("Quel est le client concerné : (il faut saisir l'ID du client) ");
            int IDClient = scanner.nextInt();
            Invoice newInvoice = new Invoice(IDServeur, IDClient);
            newInvoice.saveInvoice(connection);
            //System.out.println("Quel plat a été commandé : (il faut saisir l'ID du client) ");
            //int IDClient = scanner.nextInt();
            //Invoice newInvoice = new Invoice(IDServeur, IDClient);
            //newInvoice.saveInvoice(connection);


        } catch (SQLException exception) {
            // Ma gestion du problème
            System.out.println("Erreur de connexion à la base de données");
            exception.printStackTrace();
        }
    }

    // selection de la table la plus rentable
    private static void BestTable(Scanner sc) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/restaurant";
        String user = "postgres";
        String password = "postgres";
        int tableprix = 0;
        int prix = 0;
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement ordreSQL = connection.createStatement();
        ResultSet resultat = ordreSQL.executeQuery("select sum(quantity*pxunit) as prixTotal, \"tablefk\" as NumerodeTable from \"tablejointure\" tJ\n" +
                "join \"invoice\" on \"invoice\".\"invoiceID\" = tJ.\"invoicefk\"\n" +
                "join plat P on tJ.platfk = P.\"PlatID\"\n" +
                "group by \"tablefk\"");
        //De lister les meilleures tables par chiffre d'affaires
        resultat.next();
        prix = resultat.getInt("prixTotal");
        tableprix = resultat.getInt("NumerodeTable");
        System.out.println("Pour un montant de " + (prix));
        System.out.println("c'est la table " + (tableprix));
    }

    // selection par ordre de grandeur des plats les plus rentables
    private static void BestPlat(Scanner sc) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/restaurant";
        String user = "postgres";
        String password = "postgres";
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement ordreSQL = connection.createStatement();
        ResultSet resultat = ordreSQL.executeQuery("select \"PlatID\",\"Name\" as descriptif, sum(pxunit*quantity) as Total from \"tablejointure\"\n" +
                "join plat P on \"tablejointure\".platfk = P.\"PlatID\"\n" +
                "group by \"PlatID\" order by Total desc ");
        while (resultat.next()) {
            System.out.println(resultat.getString("PlatID") + " " + resultat.getString("Descriptif") + " " + resultat.getString("total"));
        }
    }

    // Menu principal
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println(" Que voulez vous faire?");
        System.out.println(" 1 - Inserer un nouveau client");
        System.out.println(" 2 - Insérer un nouveau serveur");
        System.out.println(" 3 - Générer une nouvelle facture");
        System.out.println(" 4 - Table la plus rentable");
        System.out.println(" 5 - Plat le plus rentable");
        int exercice = scanner.nextInt();
        scanner.nextLine();
        while (exercice != 0) {
            switch (exercice) {
                case 1:
                    Newclient(scanner);
                    break;
                case 2:
                    Newserveur(scanner);
                    break;
                case 3:
                    newInvoice(scanner);
                    break;
                case 4:
                    BestTable(scanner);
                    break;
                case 5:
                    BestPlat(scanner);
                    break;


            }
            System.out.println(" Que souhaitez vous faire?");
            exercice = scanner.nextInt();
            scanner.nextLine();

        }

    }
}



