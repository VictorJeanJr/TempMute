package victorjeanjr.tempmute.sql;

import org.bukkit.Bukkit;
import victorjeanjr.tempmute.Core;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class SQLite {

    private static SQLite instance = null;

    private static Connection connection;

    public SQLite() { instance = this; }

    public void connect() {
        try {
            File file = new File(Core.getPlugin().getDataFolder(), "database.db");
            String link = "jdbc:sqlite:" + file;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(link);
            this.createTable(TableMute.getTableCreate());
            Bukkit.getConsoleSender().sendMessage(" §aSQLite -> Connected with success.");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(" §cSQLite (Plugin disabled) -> " + e.getMessage());
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(Core.getPlugin());
        }
    }

    public void disconnect() {
        if(Objects.isNull(SQLite.getConnection())) return;
        try {
            connection.close();
            Bukkit.getConsoleSender().sendMessage(" §aSQLite -> Disconnected with success.");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(" §cSQLite -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTable(String tableCreate) throws SQLException {
        PreparedStatement preparedStatement = SQLite.getConnection().prepareStatement(tableCreate);
        preparedStatement.executeUpdate();
    }

    public static Connection getConnection() {
       if(Objects.isNull(connection)) {
           if(Objects.nonNull(SQLite.getInstance()))
               SQLite.getInstance().connect();
           else new SQLite().connect();
       } else {
           try {
               if(connection.isClosed()) {
                   if(Objects.nonNull(SQLite.getInstance()))
                       SQLite.getInstance().connect();
                   else new SQLite().connect();
               }
           } catch (Exception e) {
               Bukkit.getConsoleSender().sendMessage(" §cSQLite -> " + e.getMessage());
               e.printStackTrace();
           }
       }
       return connection;
    }

    public static SQLite getInstance() {
        return instance;
    }
}
