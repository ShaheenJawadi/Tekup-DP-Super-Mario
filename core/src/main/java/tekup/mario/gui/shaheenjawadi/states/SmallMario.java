package tekup.mario.gui.shaheenjawadi.states;

import tekup.mario.gui.shaheenjawadi.utils.LogService;

public class SmallMario implements MarioState {

    @Override
    public void jump() {
        logService.log("Mario jumps reached but not as high.");
    }

    @Override
    public void run() {

        logService.log("Mario runs but is slower.");
    }

    @Override
    public void takeDamage(MarioContext context) {

        logService.log("Mario loses a life!");
    }

    @Override
    public float getHeight() {
        return 40;
    }
}
