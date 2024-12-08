package tekup.mario.gui.shaheenjawadi.states;

public interface MarioState {
    void jump();
    void run();
    void takeDamage(MarioContext context);

    float getHeight();
}
