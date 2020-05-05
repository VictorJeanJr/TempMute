package victorjeanjr.tempmute.manager;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.api.TimeFormatter;
import victorjeanjr.tempmute.sql.TableMute;

import java.util.Collection;
import java.util.Objects;

public class PlayerMuted {

    private static Collection<PlayerMuted> playerMuted = Lists.newCopyOnWriteArrayList();

    private String playerName, staffName, reason;

    private long timeMills, calcMills;

    public PlayerMuted(String playerName) {
        this.playerName = playerName;
        this.staffName = null; this.reason = null; this.timeMills = 0; this.calcMills = 0;
        playerMuted.add(this);
    }

    public PlayerMuted(String playerName, String staffName, String reason, long timeMills, long calcMills) {
        this.playerName = playerName;
        this.staffName = staffName; this.reason = reason; this.timeMills = timeMills; this.calcMills = calcMills;
        playerMuted.add(this);
    }

    public void mute(String staffName, String reason, long calcMills, long timeMills) {
        this.staffName = staffName; this.reason = reason; this.calcMills = calcMills; this.timeMills = timeMills;
        TableMute tableMute = new TableMute(this);
        if(tableMute.contains()) tableMute.changeMute();
        else tableMute.mute();
        Message.messageMute(this).forEach(Bukkit::broadcastMessage);
    }

    public boolean isMuted() {
        if(this.getTimeMills() == -1) return true; /// -1 is permanent
        return (this.getTimeMills() - System.currentTimeMillis()) > 0;
    }

    public boolean isPermanent() {
        return this.getTimeMills() == -1;
    }

    public void unMute(String staffName) {
        Message.messageUnMute(this, staffName).forEach(Bukkit::broadcastMessage);
        this.unMute();
    }

    public void unMute() {
        new TableMute(this).unMute();
        playerMuted.remove(this);
    }

    public String getTime() {
        return this.getTimeMills() == -1 ? "Permanent" : TimeFormatter.format(this.getCalcMills());
    }

    public long getCalcMills() {
        return calcMills;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getReason() {
        if(Objects.isNull(this.reason))
            return (String) Message.getCache("Messages.mute.mute-no-reason");
        return reason;
    }

    public long getTimeMills() {
        return timeMills;
    }

    public static Collection<PlayerMuted> getPlayerMuted() {
        return playerMuted;
    }

    public static PlayerMuted get(String playerName) {
        return playerMuted.stream().filter(e -> e.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }
}
