package xyz.minerune.chatgame;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import me.seetch.format.Format;
import xyz.minerune.economy.Economy;

public class EventListener implements Listener {

    private final ChatGame plugin;

    public EventListener(ChatGame plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (plugin.getCurrent() != GameType.NONE) {
            switch (plugin.getCurrent()) {
                case FLASH:
                    if (message.equalsIgnoreCase(plugin.getResult())) {
                        for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                            players.sendMessage(Format.MATERIAL_DIAMOND.message("Игрок %0 первым отправил строку в чат.", player.getName()));
                        }

                        Economy.addMoney(player, plugin.getReward());
                        event.setCancelled();
                        plugin.resetGame();
                    }
                case DECODE:
                    if (message.equalsIgnoreCase(plugin.getResult())) {
                        for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                            players.sendMessage(Format.MATERIAL_DIAMOND.message("Игрок %0 первым составил слово %1 из заданных букв.", player.getName(), plugin.getResult()));
                        }

                        Economy.addMoney(player, plugin.getReward());
                        event.setCancelled();
                        plugin.resetGame();
                    }
                case MATH:
                    if (message.equalsIgnoreCase(plugin.getResult())) {
                        for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                            players.sendMessage(Format.MATERIAL_DIAMOND.message("Игрок %0 первым решил пример. Ответ: %1.", player.getName(), plugin.getResult()));
                        }

                        Economy.addMoney(player, plugin.getReward());
                        event.setCancelled();
                        plugin.resetGame();
                    }
                    break;
                case HANGMAN:
                    if (message.equalsIgnoreCase(plugin.getResult())) {
                        for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                            players.sendMessage(Format.MATERIAL_DIAMOND.message("Игрок %0 первым расшифровал слово %1.", player.getName(), plugin.getResult()));
                        }

                        Economy.addMoney(player, plugin.getReward());
                        event.setCancelled();
                        plugin.resetGame();
                    }
                    break;
                case NONE:
                    break;
            }
        }
    }
}
