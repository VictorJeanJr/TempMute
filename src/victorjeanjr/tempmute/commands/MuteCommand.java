package victorjeanjr.tempmute.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.util.Arrays;
import java.util.Objects;

public class MuteCommand implements CommandExecutor {

    private String command = "/mute <target> [reason]";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("permission.mute.command")) {
            if(args.length == 0) {
                sender.sendMessage(((String) Message.getCache("Messages.commands.wrong-syntax"))
                        .replace("{command}", command));
            } else {
                String reason;
                if(args.length == 1) reason = ((String) Message.getCache("Messages.mute.mute-no-reason"));
                else reason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");

                String namePlayer = args[0];
                if(Objects.isNull(PlayerMuted.get(namePlayer))) {
                    new PlayerMuted(namePlayer).mute(sender.getName(), reason, -1, -1);
                    sender.sendMessage(((String) Message.getCache("Messages.commands.success-mute"))
                            .replace("{player}", namePlayer));
                } else {
                    PlayerMuted playerMuted = PlayerMuted.get(namePlayer);
                    if(!playerMuted.isPermanent()) {
                        playerMuted.mute(sender.getName(), reason, -1, -1);
                        sender.sendMessage(((String) Message.getCache("Messages.commands.success-mute"))
                                .replace("{player}", namePlayer));
                    } else {
                        sender.sendMessage((String) Message.getCache("Messages.commands.already-muted-permanently"));
                    }
                }
            }
        } else {
            sender.sendMessage((String) Message.getCache("Messages.commands.no-have-permission"));
        }
        return false;
    }

}
