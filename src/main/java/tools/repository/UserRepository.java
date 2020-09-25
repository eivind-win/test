package tools.repository;

import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalTime;

import models.TableModel;
import models.UserModel;
import tools.DbTool;

public class UserRepository {
    /**
     * legger til bruker til databasen.
     * Denne er ikke implementert. Her må dere gjerne prøve å lage en egen servlet som kan kommunisere med
     * denne metoden.
     *
     * @param user bruker objekt som inneholder all informasjon om personen.
     *             Tips: Objektet må instansieres i en servlet før man kaller på addUser().
     * @param p    printwriter for å skrive ut html i servlet. F.eks SQL feilmeldinger eller annen info.
     */

    public static void addUser(UserModel user, PrintWriter p) {
        Connection db = null;
        PreparedStatement insertNewUser = null;
        try {
            db = DbTool.getINSTANCE().dbLoggIn(p);
            db.setCatalog("Roklubben");
            String query =
                    "INSERT INTO `user` (User_firstName, User_lastName,User_Email, User_password ) values (?,?,?,?)";

            insertNewUser = db.prepareStatement(query);
            insertNewUser.setString(1, user.getFirstName());
            insertNewUser.setString(2, user.getLastName());
            insertNewUser.setString(3, user.getUserName());
            insertNewUser.setString(4, user.getPassword());
            insertNewUser.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                db.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    /**
     * henter ut spesifikk person fra databasen
     *
     * @param username brukerens epost-addresse ("trym@example.com");
     * @param p        printwriter see metoden over.
     * @return et String objekt med eposten til brukeren.
     */

    public static String getUserName(String username, PrintWriter p) {
        Connection db = null;
        PreparedStatement prepareStatement = null;

        String toReturn = null;
        try {
            db = DbTool.getINSTANCE().dbLoggIn(p);
            ResultSet rs = null;
            String query = "SELECT * FROM Roklubben.mytable where Navn = ?";
            prepareStatement = db.prepareStatement(query);
            prepareStatement.setString(1, username);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                toReturn = rs.getString("Navn");
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;

    }

    public static TableModel getResults(String Navn, PrintWriter printWriter) {
        Connection db = null;
        PreparedStatement prepareStatement = null;
        TableModel toReturn = null;

        try {
            db = DbTool.getINSTANCE().dbLoggIn(printWriter);
            ResultSet rs = null;
            String query = "SELECT * FROM Roklubben.mytable where Navn = ?";
            prepareStatement = db.prepareStatement(query);
            prepareStatement.setString(1, Navn);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {

                Integer rank = rs.getInt("Rank");
                double Score = rs.getDouble("score");
                Integer Født = rs.getInt("Født");
                String navn = rs.getString("Navn");
                String Klubb = rs.getString("Klubb");
                Time meter2000 = rs.getTime("2000_meter");
                Time meter3000 = rs.getTime("3000_meter");
                Integer meter60 = rs.getInt("60_meter");
                Integer krhev = rs.getInt("krhev");
                Double sargeant = rs.getDouble("sargeant");
                Integer beveg = rs.getInt("beveg");

                toReturn = new TableModel(rank, Score,Født, navn, Klubb, meter2000, meter3000, meter60, krhev, sargeant, beveg);

            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


}
