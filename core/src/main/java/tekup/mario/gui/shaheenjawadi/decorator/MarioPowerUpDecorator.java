package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.MarioContext;
import tekup.mario.gui.shaheenjawadi.states.MarioState;

public abstract class MarioPowerUpDecorator implements MarioState  {
    protected MarioState marioState;

    public MarioPowerUpDecorator(MarioState marioState) {
        this.marioState = marioState;
    }

    @Override
    public void jump() {
        marioState.jump(); // Delegate to the wrapped state
    }

    @Override
    public void run() {
        marioState.run(); // Delegate to the wrapped state
    }

    @Override
    public void takeDamage(MarioContext context) {
        marioState.takeDamage(context); // Delegate to the wrapped state
    }

    @Override
    public float getHeight() {
        return marioState.getHeight(); // Delegate to the wrapped state
    }
}
