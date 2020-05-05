package victorjeanjr.tempmute.sql;

import org.bukkit.Bukkit;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableMute {

    private static String NAME_TABLE = "tempmute_mutes";
    private static String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + NAME_TABLE +
                    " (Name TEXT, Staff TEXT, Reason TEXT, TimeMillis TEXT, PRIMARY KEY(Name))";

    /// --------------------------------------------------- ///

    private PlayerMuted playerMuted;

    public TableMute(PlayerMuted playerMuted) {
        this.playerMuted = playerMuted;
    }

    public boolean contains() {
        try {
            PreparedStatement preparedStatement = SQLite.getConnection().prepareStatement(
                    "SELECT * FROM " + TableMute.getNameTable() +" WHERE Name= ?");

            preparedStatement.setString(1, this.playerMuted.getPlayerName());

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();

            preparedStatement.close();
            resultSet.close();
            return next;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(" Mute -> " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void mute() {
        try {
            PreparedStatement preparedStatement = SQLite.getConnection()
                    .prepareStatement("INSERT INTO " + TableMute.getNameTable() +
                            " (Name, Staff, Reason, TimeMillis) VALUES (?,?,?,?)");

            String millis = String.format("%s#%s", this.playerMuted.getTimeMills(), this.playerMuted.getCalcMills());

            preparedStatement.setString(1, this.playerMuted.getPlayerName());
            preparedStatement.setString(2, this.playerMuted.getStaffName());
            preparedStatement.setString(3, this.playerMuted.getReason());
            preparedStatement.setString(4, millis);

            preparedStatement.executeUpdate(); preparedStatement.close();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(" Mute -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeMute() {
        try {
            PreparedStatement preparedStatement = SQLite.getConnection().prepareStatement(
                    "UPDATE "+ TableMute.getNameTable() + " SET " +
                            "Staff = ?, Reason = ?, TimeMillis = ? WHERE Name = ?");

            String millis = String.format("%s#%s", this.playerMuted.getTimeMills(), this.playerMuted.getCalcMills());

            preparedStatement.setString(1, this.playerMuted.getStaffName());
            preparedStatement.setString(2, this.playerMuted.getReason());
            preparedStatement.setString(3, millis);
            preparedStatement.setString(4, this.playerMuted.getPlayerName());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(" Mute -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void unMute() {
        try {
            PreparedStatement preparedStatement = SQLite.getConnection()
                    .prepareStatement("DELETE FROM " + TableMute.getNameTable() + " WHERE Name = ?");

            preparedStatement.setString(1, playerMuted.getPlayerName());

            preparedStatement.execute(); preparedStatement.close();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(" UnMute -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    /// --------------------------------------------------- ///


    public PlayerMuted getPlayerMuted() {
        return playerMuted;
    }

    public static String getNameTable() {
        return NAME_TABLE;
    }

    public static String getTableCreate() {
        return TABLE_CREATE;
    }

    /// --------------------------------------------------- ///

    public static void returnAllValues() {
        try {
            Statement statement = SQLite.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TableMute.getNameTable() + ";");

            while (resultSet.next()) {
                String playerName = resultSet.getString("Name");
                String staffName = resultSet.getString("Staff");
                String reason = resultSet.getString("Reason");
                String timeMillis = resultSet.getString("TimeMillis");

                long millis = Long.parseLong(timeMillis.split("#")[0]);
                long calcMillis = Long.parseLong(timeMillis.split("#")[1]);

                new PlayerMuted(playerName, staffName, reason, millis, calcMillis);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
