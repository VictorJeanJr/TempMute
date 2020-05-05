package victorjeanjr.tempmute.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.util.Objects;

public class MuteListener implements Listener {

    @EventHandler
    public void verificationJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.isOp() || p.hasPermission("permission.notmute")) return;

        PlayerMuted playerMuted = PlayerMuted.get(p.getName());
        if(Objects.isNull(playerMuted)) return;
        if(!playerMuted.isMuted()) playerMuted.unMute();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void aSyncChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(p.isOp() || p.hasPermission("permission.notmute")) return;

        PlayerMuted playerMuted = PlayerMuted.get(p.getName());
        if(Objects.isNull(playerMuted)) return;
        if(!playerMuted.isMuted()) { playerMuted.unMute(); return; }
        e.setCancelled(true);
        p.sendMessage((String) Message.getCache("Messages.player.you-muted"));
    }

}
