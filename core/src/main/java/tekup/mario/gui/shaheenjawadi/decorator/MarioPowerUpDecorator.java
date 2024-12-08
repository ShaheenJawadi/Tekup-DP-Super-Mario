package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.MarioContext;
import tekup.mario.gui.shaheenjawadi.states.MarioState;

abstract class MarioPowerUpDecorator extends MarioContext {
    protected MarioContext mario;

    public MarioPowerUpDecorator(MarioContext mario) {
        this.mario = mario;
    }



    @Override
    public void jump() {
        mario.jump();
    }

    @Override
    public void run() {
        mario.run();
    }
}
