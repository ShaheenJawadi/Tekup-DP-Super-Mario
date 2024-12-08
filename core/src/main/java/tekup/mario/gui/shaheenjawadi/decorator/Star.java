package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.MarioContext;

public class Star extends MarioPowerUpDecorator {
    public Star(MarioContext mario) {
        super(mario);
    }

    @Override
    public void run() {
        System.out.println("Mario court avec invincibilité !");
    }
}
