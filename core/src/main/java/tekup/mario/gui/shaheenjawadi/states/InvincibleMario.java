package tekup.mario.gui.shaheenjawadi.states;

public class InvincibleMario implements MarioState {
    @Override
    public void jump() {
        logService.log("Mario jumps with invincibility!");
    }

    @Override
    public void run() {
        logService.log("Mario runs without fear!");
    }
    @Override
    public float getHeight() {
        return 70;
    }

    @Override
    public void takeDamage(MarioContext context) {
        logService.log("Mario is invincible, no damage!");
    }


}
