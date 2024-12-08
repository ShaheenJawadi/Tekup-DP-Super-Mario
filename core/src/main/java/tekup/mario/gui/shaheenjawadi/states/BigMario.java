package tekup.mario.gui.shaheenjawadi.states;

public class BigMario implements MarioState {
    @Override
    public void jump() {
        logService.log("Mario jumps higher.");
    }

    @Override
    public void run() {

        logService.log("Mario runs normally.");
    }

    @Override
    public void takeDamage(MarioContext context) {
        logService.log("Mario returns to his normal state.");
        context.setState(new NormalMario());
    }

    @Override
    public float getHeight() {
        return 70;
    }
}
