package victorjeanjr.tempmute.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import victorjeanjr.tempmute.api.Message;
import victorjeanjr.tempmute.api.TimeFormatter;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.util.Arrays;
import java.util.Objects;

public class TempMuteCommand implements CommandExecutor {

    private String command = "/tempmute <time> <target> [reason]";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("permission.infomute.command")) {
            if(args.length == 0) {
                sender.sendMessage(((String) Message.getCache("Messages.commands.wrong-syntax"))
                        .replace("{command}", command));
            } else {
                String reason;
                if(args.length < 2) {
                    sender.sendMessage(((String) Message.getCache("Messages.commands.wrong-syntax"))
                            .replace("{command}", command));
                    return false;
                } else {
                    if(args.length == 2) reason = ((String) Message.getCache("Messages.mute.mute-no-reason"));
                    else reason = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
                }
                String time = args[0], namePlayer = args[1];

                long format = TimeFormatter.getFormat(Integer.parseInt(time.replaceAll("[^0-9]", "")), time);

                if(format == -1) { sender.sendMessage("§cAn error occurred when entering the time"); return false; }

                if(Objects.isNull(PlayerMuted.get(namePlayer))) {
                    long mills = System.currentTimeMillis() + format;
                    new PlayerMuted(namePlayer).mute(sender.getName(), reason, format, mills);
                    sender.sendMessage(((String) Message.getCache("Messages.commands.success-mute"))
                            .replace("{player}", namePlayer));
                } else {
                    PlayerMuted playerMuted = PlayerMuted.get(namePlayer);
                    long mills = System.currentTimeMillis() + format;
                    playerMuted.mute(sender.getName(), reason, format, mills);
                    sender.sendMessage(((String) Message.getCache("Messages.commands.success-mute"))
                            .replace("{player}", namePlayer));
                }
            }
        } else {
            sender.sendMessage((String) Message.getCache("Messages.commands.no-have-permission"));
        }
        return false;
    }

}
