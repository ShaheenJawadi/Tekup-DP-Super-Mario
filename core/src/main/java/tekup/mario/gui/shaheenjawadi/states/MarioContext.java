package tekup.mario.gui.shaheenjawadi.states;

import tekup.mario.gui.shaheenjawadi.decorator.MarioPowerUpDecorator;

public class MarioContext {
    private MarioState state;

    public MarioContext() {
        this.state = new NormalMario();
    }
    public void applyPowerUp(MarioPowerUpDecorator powerUp) {
        this.state =   powerUp;
    }
    public void setState(MarioState state) {
        this.state = state;
    }

    public MarioState getState() {
        return state;
    }

    public void jump() {
        state.jump();
    }

    public void run() {
        state.run();
    }

    public void takeDamage() {
        state.takeDamage(this);
    }

    public float getCurrentHeight() {
        return state.getHeight();
    }

}
