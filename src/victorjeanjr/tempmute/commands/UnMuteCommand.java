package victorjeanjr.tempmute.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.util.Arrays;
import java.util.Objects;

public class UnMuteCommand implements CommandExecutor {

    private String command = "/unmute <target>";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("permission.unmute.command")) {
            if(args.length == 0) {
                sender.sendMessage(((String) Message.getCache("Messages.commands.wrong-syntax"))
                        .replace("{command}", command));
            } else {
                String namePlayer = args[0];
                if(Objects.nonNull(PlayerMuted.get(namePlayer))) {
                    PlayerMuted playerMuted = PlayerMuted.get(namePlayer);
                    playerMuted.unMute(sender.getName());
                    sender.sendMessage(((String) Message.getCache("Messages.commands.success-unmute"))
                            .replace("{player}", namePlayer));
                } else {
                    sender.sendMessage(((String) Message.getCache("Messages.commands.player-unknown"))
                            .replace("{player}", namePlayer));
                }
            }
        } else {
            sender.sendMessage((String) Message.getCache("Messages.commands.no-have-permission"));
        }
        return false;
    }

}
