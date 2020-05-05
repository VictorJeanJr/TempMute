package victorjeanjr.tempmute.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.util.Objects;

public class InfoMuteCommand implements CommandExecutor {

    private String command = "/infomute <target>";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("permission.infomute.command")) {
            if(args.length == 0) {
                sender.sendMessage(((String) Message.getCache("Messages.commands.wrong-syntax"))
                        .replace("{command}", command));
            } else {
                String namePlayer = args[0];
                if(Objects.nonNull(PlayerMuted.get(namePlayer))) {
                    PlayerMuted playerMuted = PlayerMuted.get(namePlayer);
                    Message.infoMute(playerMuted).forEach(sender::sendMessage);
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
