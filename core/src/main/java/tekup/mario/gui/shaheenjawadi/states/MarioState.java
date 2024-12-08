package tekup.mario.gui.shaheenjawadi.states;

import tekup.mario.gui.shaheenjawadi.utils.LogService;

public interface MarioState {
    void jump();
    void run();
    void takeDamage(MarioContext context);

    float getHeight();
    LogService logService = LogService.getInstance();
}
