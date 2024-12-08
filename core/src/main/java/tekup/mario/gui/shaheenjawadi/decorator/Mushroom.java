package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.MarioContext;

public class Mushroom extends MarioPowerUpDecorator {
    public Mushroom(MarioContext mario) {
        super(mario);
    }

    @Override
    public void jump() {
        System.out.println("Mario saute avec plus de puissance !");
    }
}
