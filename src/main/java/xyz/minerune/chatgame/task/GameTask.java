package xyz.minerune.chatgame.task;

import cn.nukkit.scheduler.Task;
import xyz.minerune.chatgame.ChatGame;
import xyz.minerune.chatgame.GameType;

import java.util.List;

public class GameTask extends Task {

    private final ChatGame plugin;
    private final List<GameType> games;
    private int index = 0;

    public GameTask(ChatGame plugin, List<GameType> games) {
        this.plugin = plugin;
        this.games = games;
    }

    @Override
    public void onRun(int i) {
        switch (games.get(index)) {
            case FLASH:
                plugin.flash();
                break;
            case DECODE:
                plugin.decode();
                break;
            case MATH:
                plugin.math();
                break;
            case HANGMAN:
                plugin.hangman();
                break;
        }
        index++;
        if (index >= games.size()) {
            index = 0;
        }
    }
}
