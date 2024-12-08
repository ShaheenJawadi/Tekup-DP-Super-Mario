package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.BigMario;
import tekup.mario.gui.shaheenjawadi.states.MarioContext;
import tekup.mario.gui.shaheenjawadi.states.MarioState;

public class Mushroom extends MarioPowerUpDecorator {
    public Mushroom() {
        super(new BigMario());
    }

    @Override
    public void jump() {
        System.out.println("Mario saute avec plus de puissance !");
    }
}
