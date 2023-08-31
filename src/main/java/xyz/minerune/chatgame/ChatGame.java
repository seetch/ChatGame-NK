package xyz.minerune.chatgame;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import me.seetch.format.Format;
import xyz.minerune.chatgame.task.GameTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChatGame extends PluginBase {

    public String[] words = {"Камень", "Гранит", "Диорит", "Андезит", "Кальцит", "Туф", "Дерн", "Земля", "Подзол", "Булыжник", "Бедрок", "Песок", "Страж", "Хоглин", "Попугай", "Гравий", "Дуб", "Ель", "Береза", "Акация", "Хвоя", "Фантом", "Шалкер", "Губка", "Корова", "Стекло", "Песчаник", "Паутина", "Трава", "Папоротник", "Разбойник", "Шешуйница", "Азалия", "Шерсть", "Одуванчик", "Мак", "Ромашка", "Ландыш", "Скелет", "Ламинария", "Бамбук", "Кирпичи", "Лиса", "Обсидиан", "Факел", "Хорус", "Пурпур", "Спавнер", "Сундук", "Верстак", "Печь", "Лошадь", "Лестница", "Снег", "Лед", "Кактус", "Глина", "Проигрыватель", "Тыква", "Незерак", "Ведьма", "Зомби", "Иссушитель", "Дракон", "Базальт", "Цепь", "Арбуз", "Ифрит", "Гаст", "Оцелот", "Лианы", "Мицелий", "Лягушка", "Хранитель", "Тихоня", "Кувшинка", "Эндерняк", "Свинья", "Эндер-сундук", "Маяк", "Наковальня", "Барьер", "Свет", "Терракота", "Тропинка", "Подсолнух", "Сирень", "Пион", "Призмарин", "Магма", "Бетон", "Кораллы", "Поршень", "Наблюдатель", "Воронка", "Раздатчик", "Выбрасыватель", "Кафедра", "Мишень", "Рычаг", "Громоотвод", "Крюк", "Вагонетка", "Динамит", "Кролик", "Рельсы", "Седло", "Вагонетка", "Элитры", "Огниво", "Яблоко", "Лук", "Стрела", "Уголь", "Лосось", "Алмаз", "Пустыня", "Изумруд", "Лазурит", "Палка", "Нить", "Кошка", "Пляж", "Инвентарь", "Перо", "Коза", "Порох", "Пшеница", "Хлеб", "Кольчуга", "Кремень", "Картина", "Ведро", "Снежок", "Кожа", "Кирпич", "Спрут", "Бумага", "Саванна", "Край", "Пчела", "Лама", "Книга", "Пустота", "Река", "Яйцо", "Компас", "Мешок", "Удочка", "Часы", "Иглобрюх", "Кость", "Сахар", "Торт", "Курица", "Дельфин", "Панда", "Печенье", "Лодка", "Карта", "Ножницы", "Иглобрюх", "Стейк", "Курятина", "Бутылочка", "Зельеварка", "Котел", "Рамка", "Волк", "Паук", "Морковь", "Картофель", "Тотем", "Тайга", "Череп", "Алекс", "Шерепаха", "Аксолотль", "Океан", "Фейерверк", "Поводок", "Тундра", "Бирка", "Свекла", "Щит", "Пластинка", "Равнины", "Трезубец", "Арбалет", "Компостница", "Бочка", "Лес", "Болото", "Коптильня", "Точило", "Камнерез", "Колокол", "Фонарь", "Костер", "Джунгли", "Улей", "Магнетит", "Чернит", "Свеча", "Капельник", "Зелье", "Стив", "Молния", "Ад", "MINERUNE"};

    public GameType current;
    public int reward;
    public String result;

    @Override
    public void onEnable() {
        this.getServer().getScheduler().scheduleRepeatingTask(new GameTask(this, Arrays.asList(GameType.FLASH, GameType.DECODE, GameType.MATH, GameType.HANGMAN)), 20 * 60 * 5);
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        this.current = GameType.NONE;
        this.reward = 0;
        this.result = "";
    }

    public void flash() {
        setCurrent(GameType.FLASH);

        int reward = getRandomNumber(1, 30);
        setReward(reward);
        String result = randomString(7, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        setResult(result);

        for (Player player : this.getServer().getOnlinePlayers().values()) {
            player.sendMessage(Format.MATERIAL_DIAMOND.message("Первым отправьте строку %0 в чат и получите %1$", result, String.valueOf(reward)));
        }
    }

    public String randomString(int length, String characterSet) {
        return IntStream.range(0, length).map(i -> new SecureRandom().nextInt(characterSet.length())).mapToObj(randomInt -> characterSet.substring(randomInt, randomInt + 1)).collect(Collectors.joining());
    }

    public String randomWord() {
        Random random = new Random();
        int index = random.nextInt(words.length);
        return words[index];
    }

    public void decode() {
        setCurrent(GameType.DECODE);

        int reward = getRandomNumber(50, 100);
        setReward(reward);
        String word = randomWord();
        setResult(word);

        String shuffled = shuffle(word);

        for (Player player : this.getServer().getOnlinePlayers().values()) {
            player.sendMessage(Format.MATERIAL_DIAMOND.message("Первым составьте слово из заданных букв %0 и получите %1$", shuffled, String.valueOf(reward)));
        }
    }

    public String shuffle(String string) {
        List<Character> list = string.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        list.forEach(sb::append);

        return sb.toString();
    }

    public void math() {
        setCurrent(GameType.MATH);

        int var1 = getRandomNumber(1, 123);
        int var2 = getRandomNumber(1, 123);
        int reward = 0;
        String example = "";

        switch (getRandomNumber(0, 2)) {
            case 0:
                reward = getRandomNumber(10, 50);
                setReward(reward);
                example = var1 + "+" + var2;
                setResult(equationSolver(example));
                break;
            case 1:
                reward = getRandomNumber(10, 50);
                setReward(reward);
                example = var1 + "-" + var2;
                setResult(equationSolver(example));
                break;
            case 2:
                reward = getRandomNumber(30, 100);
                setReward(reward);
                example = var1 + "*" + var2;
                setResult(equationSolver(example));
                break;
        }

        for (Player player : this.getServer().getOnlinePlayers().values()) {
            player.sendMessage(Format.MATERIAL_DIAMOND.message("Первым решите пример %0 и получите %1$", example, String.valueOf(reward)));
        }
    }

    public void hangman() {
        setCurrent(GameType.HANGMAN);

        int reward = getRandomNumber(50, 100);
        setReward(reward);
        String word = randomWord();
        setResult(word);

        String changed = change(word);

        for (Player player : this.getServer().getOnlinePlayers().values()) {
            player.sendMessage(getResult());
            player.sendMessage(Format.MATERIAL_DIAMOND.message("Первым расшифруйте слово %0 и получите %1$", changed, String.valueOf(reward)));
        }
    }

    private String change(String word) {
        char[] characters = word.toCharArray();
        for (int i = 1; i <= 2; i++) {
            int rand = (int) (Math.random() * word.length());
            word = word.replace(characters[rand], '_');
        }
        return word;
    }

    public String equationSolver(String var1) {
        ScriptEngineManager var2 = new ScriptEngineManager();
        ScriptEngine var3 = var2.getEngineByName("JavaScript");

        try {
            var1 = String.valueOf(var3.eval(var1));
        } catch (ScriptException var5) {
            var5.printStackTrace();
        }

        return var1;
    }

    public GameType getCurrent() {
        return current;
    }

    public int getReward() {
        return reward;
    }

    public String getResult() {
        return result;
    }

    public void setCurrent(GameType current) {
        this.current = current;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void resetGame() {
        this.current = GameType.NONE;
        this.reward = 0;
        this.result = "";
    }
}
