package victorjeanjr.tempmute;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.commands.InfoMuteCommand;
import victorjeanjr.tempmute.commands.MuteCommand;
import victorjeanjr.tempmute.commands.TempMuteCommand;
import victorjeanjr.tempmute.commands.UnMuteCommand;
import victorjeanjr.tempmute.listener.MuteListener;
import victorjeanjr.tempmute.sql.SQLite;
import victorjeanjr.tempmute.sql.TableMute;

import java.io.File;
import java.util.Objects;

public class Core extends JavaPlugin {

    private static Core core;
    private static Plugin plugin;

    @Override
    public void onLoad() {
        File file = new File("plugins/" + this.getDataFolder().getName());
        if(!file.exists()) file.mkdirs();
    }

    @Override
    public void onEnable() {
        core = this;
        plugin = this;

        this.enabling();

        Bukkit.getConsoleSender().sendMessage("§aTempMute | Created by VictorJeanJr -> Enabled with success.");
    }

    @Override
    public void onDisable() {
        if(Objects.nonNull(SQLite.getInstance())) SQLite.getInstance().disconnect();

        Bukkit.getConsoleSender().sendMessage("§cTempMute | Created by VictorJeanJr -> Disabled with success.");
    }

    private void enabling() {
        Message.getConfig().saveDefaultConfig();
        new SQLite().connect();

        this.getCommand("tempmute").setExecutor(new TempMuteCommand());
        this.getCommand("mute").setExecutor(new MuteCommand());
        this.getCommand("unmute").setExecutor(new UnMuteCommand());
        this.getCommand("infomute").setExecutor(new InfoMuteCommand());

        Bukkit.getPluginManager().registerEvents(new MuteListener(), this);
        TableMute.returnAllValues();
    }

    public static Core getCore() {
        return core;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
