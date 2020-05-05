package victorjeanjr.tempmute.api;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.entity.Player;
import victorjeanjr.tempmute.manager.PlayerMuted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {

    private static Config config = new Config("config.yml");
    private static HashMap<String, Object> cache = Maps.newHashMap();

    public static List<String> infoMute(PlayerMuted playerMuted) {
        List<String> messageList = SerializationUtils.clone(Message.getCache("Messages.commands.infomute"));
        messageList.replaceAll(e -> e.replace("{player}", playerMuted.getPlayerName())
                .replace("{staff}", playerMuted.getStaffName())
                .replace("{reason}", playerMuted.getReason())
                .replace("{time}", playerMuted.getTime()));
        return messageList;
    }

    public static List<String> messageMute(PlayerMuted playerMuted) {
        List<String> messageList = SerializationUtils.clone(Message.getCache("Messages.message-mute"));
        messageList.replaceAll(e -> e.replace("{player}", playerMuted.getPlayerName())
                .replace("{staff}", playerMuted.getStaffName())
                .replace("{reason}", playerMuted.getReason())
                .replace("{time}", playerMuted.getTime()));
        return messageList;
    }

    public static List<String> messageUnMute(PlayerMuted playerMuted, String staffName) {
        List<String> messageList = SerializationUtils.clone(Message.getCache("Messages.message-unmute"));
        messageList.replaceAll(e -> e.replace("{player}", playerMuted.getPlayerName())
                .replace("{staff}", staffName)
                .replace("&", "§"));
        return messageList;
    }

    public static <T> T getCache(String path) {
        if(cache.containsKey(path)) return (T) cache.get(path);
        T object = (T) config.get(path);
        if(object instanceof String) {
            object = (T) ((String)object).replace("&", "§");
        } else if(object instanceof List) {
            ((List<String>)object).replaceAll(e -> e.replace("&", "§"));
        }
        cache.put(path, object);
        return object;
    }

    public static Config getConfig() {
        return config;
    }
}
