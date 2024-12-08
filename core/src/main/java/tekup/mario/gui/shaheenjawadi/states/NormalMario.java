package tekup.mario.gui.shaheenjawadi.states;

import tekup.mario.gui.shaheenjawadi.utils.LogService;

public class NormalMario implements MarioState {

    @Override
    public void jump() {
        logService.log("Mario jumps normally.");
    }

    @Override
    public void run() {
        logService.log("Mario runs normally.");
    }

    @Override
    public void takeDamage(MarioContext context) {

        context.setState(new SmallMario());
    }

    @Override
    public float getHeight() {
        return 55;
    }
}
