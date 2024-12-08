package tekup.mario.gui.shaheenjawadi.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

public class LogService {
    private static final int MAX_LOGS = 5;
    private final LinkedList<String> logs;

    private static LogService instance;


    private LogService() {
        logs = new LinkedList<>();
    }

    public static LogService getInstance() {
        if (instance == null) {
            instance = new LogService();
        }
        return instance;
    }

    public void log(String message) {
        if (logs.size() >= MAX_LOGS) {
            logs.removeFirst();
        }
        logs.add(message);
    }

    public void renderLogs(SpriteBatch batch, BitmapFont font) {
        int y = 20;
        for (String log : logs) {
            font.draw(batch, log, 10, y);
            y += 20;
        }
    }
}
